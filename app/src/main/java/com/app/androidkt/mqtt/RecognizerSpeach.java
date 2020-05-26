package com.app.androidkt.mqtt;

import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.AsyncTask;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import com.app.androidkt.mqtt.recognizer.DataFiles;
import com.app.androidkt.mqtt.recognizer.Grammar;
import com.app.androidkt.mqtt.recognizer.PhonMapper;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Queue;
import java.util.UUID;

import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;

public class RecognizerSpeach implements RecognitionListener {

    private static final String TAG = "Recognizer ser";

    private static final String COMMAND_SEARCH = "command";
    private static final String KWS_SEARCH = "hotword";

    private final Handler mHandler = new Handler();
    private final Queue<String> mSpeechQueue = new LinkedList<String>();

    private SpeechRecognizer mRecognizer;
    private TextToSpeech mTextToSpeech;
    //private List<Device> devices;

    private static Context context;

    private final Runnable mStopRecognitionCallback = new Runnable() {
        @Override
        public void run() {
            stopRecognition();
        }
    };

    private final TextToSpeech.OnUtteranceCompletedListener mUtteranceCompletedListener = new TextToSpeech.OnUtteranceCompletedListener() {
        @Override
        public void onUtteranceCompleted(String utteranceId) {
            synchronized (mSpeechQueue) {
                mSpeechQueue.poll();
                if (mSpeechQueue.isEmpty()) {
                    mRecognizer.startListening(KWS_SEARCH);
                }
            }
        }
    };

    public RecognizerSpeach(Context context){
        this.context = context;

        //Device light = new Device(0,"light");
        //Device temp = new Device(0,"temp");
//        devices.add(light);
//        devices.add(temp);

        mTextToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.ERROR) return;
                if (mTextToSpeech.isLanguageAvailable(Locale.getDefault()) == TextToSpeech.LANG_AVAILABLE) {
                    mTextToSpeech.setLanguage(Locale.getDefault());
                }
                mTextToSpeech.setOnUtteranceCompletedListener(mUtteranceCompletedListener);
            }
        });
        setupRecognizer();
    }

    public void onDestroy() {
        if (mRecognizer != null) mRecognizer.cancel();
        mTextToSpeech.shutdown();
        setupRecognizer();
    }

    protected void setupRecognizer() {
        final String hotword = "номад";
        new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    final String[] names = new String[2];
                    names[0] = "свет";
                    names[1] = "температура";
//                    for (int i = 0; i < names.length; i++) {
//                        names[i] = devices.get(i).name;
//                    }
                    PhonMapper phonMapper = new PhonMapper(context.getAssets().open("dict/ru/hotwords"));
                    Grammar grammar = new Grammar(names, phonMapper);
                    grammar.addWords(hotword);
                    DataFiles dataFiles = new DataFiles(context.getPackageName(), "ru");
                    File hmmDir = new File(dataFiles.getHmm());
                    File dict = new File(dataFiles.getDict());
                    File jsgf = new File(dataFiles.getJsgf());
                    copyAssets(hmmDir);
                    saveFile(jsgf, grammar.getJsgf());
                    saveFile(dict, grammar.getDict());
                    mRecognizer = SpeechRecognizerSetup.defaultSetup()
                            .setAcousticModel(hmmDir)
                            .setDictionary(dict)
                            .setBoolean("-remove_noise", false)
                            .setKeywordThreshold(1e-7f)
                            .getRecognizer();
                    mRecognizer.addKeyphraseSearch(KWS_SEARCH, hotword);
                    mRecognizer.addGrammarSearch(COMMAND_SEARCH, jsgf);
                } catch (IOException e) {
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception ex) {
                if (ex != null) {
                    onRecognizerSetupError(ex);
                } else {
                    onRecognizerSetupComplete();
                }
            }
        }.execute();
    }

    private void onRecognizerSetupComplete() {
        Toast.makeText(context, "Ready", Toast.LENGTH_SHORT).show();
        mRecognizer.addListener(this);
        mRecognizer.startListening(KWS_SEARCH);
    }

    private void onRecognizerSetupError(Exception ex) {
        Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void copyAssets(File baseDir) throws IOException {
        String[] files = context.getAssets().list("hmm/ru");

        for (String fromFile : files) {
            File toFile = new File(baseDir.getAbsolutePath() + "/" + fromFile);
            InputStream in = context.getAssets().open("hmm/ru/" + fromFile);
            FileUtils.copyInputStreamToFile(in, toFile);
            toFile.createNewFile();
        }
    }

    private void saveFile(File f, String content) throws IOException {
        File dir = f.getParentFile();
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Cannot create directory: " + dir);
        }
        FileUtils.writeStringToFile(f, content, "UTF8");
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.d(TAG, "onBeginningOfSpeech");
    }

    @Override
    public void onEndOfSpeech() {
        Log.d(TAG, "onEndOfSpeech");
        if (mRecognizer.getSearchName().equals(COMMAND_SEARCH)) {
            mRecognizer.stop();
        }
    }

    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if (hypothesis == null) return;
        String text = hypothesis.getHypstr();
        Log.d(TAG + "PartRes", text);
        if (KWS_SEARCH.equals(mRecognizer.getSearchName())) {
            startRecognition();
        } else {
            Log.d(TAG, text);
        }
    }

    @Override
    public void onResult(Hypothesis hypothesis) {
        //mMicView.setBackgroundResource(R.drawable.background_big_mic);
        mHandler.removeCallbacks(mStopRecognitionCallback);
        String text = hypothesis != null ? hypothesis.getHypstr() : null;
        Log.d(TAG, "onResult " + text);
        if (text != null) {
            //Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            if (text .charAt(0) == 'с') MainActivity.changeLight();
            else speak("температура на улице "+MainActivity.getweather().substring(0,MainActivity.getweather().length() - 1));
        }
        if (COMMAND_SEARCH.equals(mRecognizer.getSearchName())) {
            mRecognizer.startListening(KWS_SEARCH);
        }
    }

    public void startStopRecognition() {
        if (mRecognizer == null) return;
        if (KWS_SEARCH.equals(mRecognizer.getSearchName())) {
            startRecognition();
        } else {
            stopRecognition();
        }
    }

    private synchronized void startRecognition() {
        if (mRecognizer == null || COMMAND_SEARCH.equals(mRecognizer.getSearchName())) return;
        mRecognizer.cancel();
        new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME).startTone(ToneGenerator.TONE_CDMA_PIP, 200);
        post(400, new Runnable() {
            @Override
            public void run() {
                //mMicView.setBackgroundResource(R.drawable.background_big_mic_green);
                mRecognizer.startListening(COMMAND_SEARCH, 3000);
                Log.d(TAG, "Listen commands");
                post(4000, mStopRecognitionCallback);
            }
        });
    }

    public synchronized void stopRecognition() {
        if (mRecognizer == null || KWS_SEARCH.equals(mRecognizer.getSearchName())) return;
        mRecognizer.stop();
        //mMicView.setBackgroundResource(R.drawable.background_big_mic);
    }

    private void post(long delay, Runnable task) {
        mHandler.postDelayed(task, delay);
    }

    public void speak(String text) {
        synchronized (mSpeechQueue) {
            mRecognizer.stop();
            mSpeechQueue.add(text);
            HashMap<String, String> params = new HashMap<String, String>(2);
            params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, UUID.randomUUID().toString());
            params.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_MUSIC));
            params.put(TextToSpeech.Engine.KEY_FEATURE_NETWORK_SYNTHESIS, "true");
            mTextToSpeech.speak(text, TextToSpeech.QUEUE_ADD, params);
        }

    }
}
