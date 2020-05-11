package com.app.androidkt.mqtt.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.app.androidkt.mqtt.MainActivity;
import com.app.androidkt.mqtt.PahoMqttClient;
import com.app.androidkt.mqtt.R;
import com.app.androidkt.mqtt.StartActivity;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private Bundle savedState = null;

    static private PahoMqttClient pahoMqttClient;
    private MqttAndroidClient client;

    private static EditText temp,time, clientCon, weeather;
    private static Switch mainLight, extraLight;
    private static TextView song;
    private static ImageButton previousM, followingM, music;
    private static CheckBox mute;
    private static SeekBar volume;
    private static Spinner gender;

    private static boolean playMusic, pauseMusic;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        playMusic = false;
        pauseMusic = false;
        pahoMqttClient = MainActivity.getPahoMqttClient();
        client = MainActivity.getMqttAndroidClient();
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        temp = (EditText) root.findViewById(R.id.text_temp);
        time = (EditText) root.findViewById(R.id.text_time);
        clientCon = (EditText) root.findViewById(R.id.text_client);
        weeather = (EditText) root.findViewById(R.id.text_tempOut);

        mainLight = (Switch) root.findViewById(R.id.mainlight);
        extraLight= (Switch) root.findViewById(R.id.extralight);

        song = (TextView) root.findViewById(R.id.musicTitle);
        previousM = (ImageButton) root.findViewById(R.id.media_previous);
        followingM = (ImageButton) root.findViewById(R.id.media_following);
        music = (ImageButton) root.findViewById(R.id.media_play);

        volume = (SeekBar) root.findViewById(R.id.volume);
        mute = (CheckBox) root.findViewById(R.id.mute);

        gender = (Spinner) root.findViewById(R.id.gender);
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(((MainActivity) getActivity()).getApplicationContext(), R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(adapter);
        // Set a checked change listener for switch button
        mainLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    publishEvent("mainLight", "ON", "Main light is turn ON", "Sorry, Main light doesn't turn ON");
                }
                else {
                    publishEvent("mainLight", "OFF", "Main light is turn OFF", "Sorry, Main light doesn't turn OFF");
                }
            }
        });
        extraLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    publishEvent("extraLight", "ON", "Extra light is turn ON", "Sorry, Extra light doesn't turn ON");
                }
                else {
                    publishEvent("extraLight", "OFF", "Extra light is turn OFF", "Sorry, Extra light doesn't turn OFF");
                }
            }
        });

        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playMusic == false || pauseMusic == true) {
                    if (playMusic == false) {
                        publishEvent("music", "ON", "", "");
                        playMusic = true;
                    }
                    publishEvent("pause", "OFF", "Music started to play", "Music didn't start playing");
                    pauseMusic = false;
                    mute.setChecked(false);
                } else {
                    publishEvent("pause","ON", "Music stopped  to play", "Music didn't stop playing");
                }
            }
        });
        previousM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playMusic == true) {
                    publishEvent("previous", "ON", "Previous song", "Сould not switch to the previous song");
                    pauseMusic = false;
                    mute.setChecked(false);
                }
                else  Toast.makeText(((MainActivity) getActivity()).getApplicationContext(), "Music doesn't play, firstly turn on music", Toast.LENGTH_SHORT).show();
            }
        });
        followingM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playMusic == true){
                    publishEvent("following","ON", "Next song", "Could not switch to the next song");
                    pauseMusic = false;
                    mute.setChecked(false);
                }
                else  Toast.makeText(((MainActivity) getActivity()).getApplicationContext(), "Music doesn't play, firstly turn on music", Toast.LENGTH_SHORT).show();
            }
        });
        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub
                float count = ((float)progress)/ ((float)100);
                //temp.setText(count);
                //Toast.makeText(((MainActivity) getActivity()).getApplicationContext(), "Volume set " + (String.valueOf(progress)) + "%",Toast.LENGTH_LONG).show();
                publishEvent("volume" , String.valueOf(count), "Volume set " + (String.valueOf(progress)) + "%", "I cannot do it");
            }
        });

        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mute.isChecked()) {
                    publishEvent("mute", "ON", "Mute volume", "Сould not mute volume");
                } else{
                    publishEvent("mute","OFF", "Not mute volume", "Could not turn off mute volume");
                }
            }
        });

        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
                String[] choose = getResources().getStringArray(R.array.gender);
                publishEvent("voice", choose[selectedItemPosition], "Ваш выбор: " + choose[selectedItemPosition], "Выбор не удался");
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public static void getMessage(String top, String msg){
        char[] s1 = msg.toCharArray();
        //temp.setText(temp.getText() + " | "+ top + " " + msg);
        switch (top){
            case "temp":
                temp.setText(msg + " °C");
                break;
            case "time":
                time.setText(msg);
                break;
            case "client":
                clientCon.setText(msg);
                break;
            case "weather":
                weeather.setText(msg);
                break;
            case "mainLight":
                if (Arrays.equals(s1,"ON".toCharArray()))
                    mainLight.setChecked(true);
                if (Arrays.equals(s1,"OFF".toCharArray()))
                    mainLight.setChecked(false);
                break;
            case "extraLight":
                if (Arrays.equals(s1,"ON".toCharArray()))
                    extraLight.setChecked(true);
                if (Arrays.equals(s1,"OFF".toCharArray()))
                    extraLight.setChecked(false);
                break;
            case "song":
                song.setText(msg);
                break;
            case "voice": //gender
                if (Arrays.equals(s1,"Woman".toCharArray()))
                    gender.setSelection(0);
                if (Arrays.equals(s1,"Man".toCharArray()))
                    gender.setSelection(1);

                break;
            case "volume":
                float co = Float.parseFloat(msg) * 100;
                int  countVolume = (int)(co);
                volume.setProgress(countVolume);
                break;
            case "pause":
                if (Arrays.equals(s1,"ON".toCharArray()))
                    pauseMusic = true;
                if (Arrays.equals(s1,"OFF".toCharArray()))
                    pauseMusic = false;
                break;
            case "mute":
                if (Arrays.equals(s1,"ON".toCharArray()))
                    mute.setChecked(true);
                if (Arrays.equals(s1,"OFF".toCharArray()))
                    mute.setChecked(false);
                break;
            case "music":
                if (Arrays.equals(s1,"ON".toCharArray())){
                    playMusic = true;
                }
                if (Arrays.equals(s1,"OFF".toCharArray()))
                    playMusic = false;
                break;
            default:
                break;

        }

    }
    private void publishEvent(String top, String msg, String successful, String unsuccessful){
        try {
            pahoMqttClient.publishMessage(client, msg, 1, top);
            if (successful != "") Toast.makeText(((MainActivity) getActivity()).getApplicationContext(), successful, Toast.LENGTH_SHORT).show();
        } catch (MqttException e) {
            if (unsuccessful != "") Toast.makeText(((MainActivity) getActivity()).getApplicationContext(), unsuccessful, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            if (unsuccessful != "") Toast.makeText(((MainActivity) getActivity()).getApplicationContext(), unsuccessful, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }


}