package com.app.androidkt.mqtt;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;


@RequiresApi(api = Build.VERSION_CODES.N)
public class RecognizerService extends TileService {

    private static RecognizerSpeach recognizerSpeach;

    private Context context;

    private final Handler mHandler = new Handler();

    private boolean stat;

    IBinder mBinder;


    public void startStopRecognition() {
        recognizerSpeach.startStopRecognition();
    }

    public void stopRecognition() {
        recognizerSpeach.stopRecognition();
    }

    public void setup() {
    }

    public class LocalBinder extends Binder {
        public RecognizerService getServerInstance() {
            return RecognizerService.this;
        }
    }

    @Override
    public void onClick() {
        super.onClick();
        Icon icon = Icon.createWithResource(getApplicationContext(), R.drawable.big_mic);
        getQsTile().setIcon(icon);
        getQsTile().updateTile();

        Toast.makeText(getApplicationContext(),"click!", Toast.LENGTH_LONG);
        Log.d("RecognizerService", "Click QS");

        Intent calendarIntent = new Intent(this, MainActivity.class);
        startActivityAndCollapse(calendarIntent);
    }

    @Override
    public void onStartListening() {
        Tile tile = getQsTile();
        tile.setIcon(Icon.createWithResource(this,R.drawable.big_mic));
        tile.setLabel(getString(R.string.app_name));
        tile.setContentDescription(getString(R.string.tile_content_description));
        tile.setState(Tile.STATE_ACTIVE);
        tile.updateTile();
    }

    @Override
    public void onCreate() {



        super.onCreate();


        stat = false;
        mBinder = new LocalBinder();
        recognizerSpeach = new RecognizerSpeach(this);
        //recognizerSpeach.setupRecognizer();
    }

    public RecognizerService() {
    }

    @Override
    public void onDestroy() {
        recognizerSpeach.onDestroy();

        Log.d("RecognizerService", "STOP");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Integer i =2;
        i+=1;
        return mBinder;
        //Toast.makeText(getApplicationContext(), "binding", Toast.LENGTH_SHORT).show();
    }


}
