package com.app.androidkt.mqtt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.androidkt.mqtt.Constants;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private static PahoMqttClient pahoMqttClient;
    private static MqttAndroidClient client;
    private String TAG = "MainActivity";

    private EditText textMessage,textTopic, subscribeTopic, unSubscribeTopic, textTemp, textTime, textClient;
    private Button publishMessage, subscribe, unSubscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pahoMqttClient = new PahoMqttClient();
        client = pahoMqttClient.getMqttClient(getApplicationContext(), Constants.MQTT_BROKER_URL, Constants.CLIENT_ID);

        textTemp   = (EditText) findViewById(R.id.text_temp);
        textTime   = (EditText) findViewById(R.id.text_time);
        textClient = (EditText) findViewById(R.id.text_client);

        textMessage    = (EditText) findViewById(R.id.pub_msg);
        textTopic      = (EditText) findViewById(R.id.pub_topic);
        publishMessage = (Button)   findViewById(R.id.pun_button);

        subscribeTopic = (EditText) findViewById(R.id.sub_topic);
        subscribe      = (Button)   findViewById(R.id.sub_button);

        unSubscribeTopic = (EditText) findViewById(R.id.unsub_topic);
        unSubscribe      = (Button)   findViewById(R.id.unsub_button);

        publishMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = textMessage.getText().toString().trim();
                String top = textTopic.getText().toString().trim();
                if (!msg.isEmpty() || !top.isEmpty()) {
                    try {
                        pahoMqttClient.publishMessage(client, msg, 1, top);
                        Toast.makeText(getApplicationContext(), "\"" + msg + "\" has been sent to " + top, Toast.LENGTH_SHORT).show();
                    } catch (MqttException e) {
                        Toast.makeText(getApplicationContext(), "Error "+ e.toString(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error "+ e.toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (msg.isEmpty()) Toast.makeText(getApplicationContext(), "Message is empty!", Toast.LENGTH_SHORT).show();
                    else if (top.isEmpty()) Toast.makeText(getApplicationContext(), "Topic is empty!", Toast.LENGTH_SHORT).show();
                    else Toast.makeText(getApplicationContext(), "Message or Topic is empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String topic = subscribeTopic.getText().toString().trim();
                if (!topic.isEmpty()) {
                    try {
                        pahoMqttClient.subscribe(client, topic, 1);
                        Toast.makeText(getApplicationContext(), "Subscribed to " + topic, Toast.LENGTH_SHORT).show();
                    } catch (MqttException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error "+ e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        unSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String topic = unSubscribeTopic.getText().toString().trim();
                if (!topic.isEmpty()) {
                    try {
                        pahoMqttClient.unSubscribe(client, topic);
                        Toast.makeText(getApplicationContext(), "unSubscribed to " + topic, Toast.LENGTH_SHORT).show();
                    } catch (MqttException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error "+ e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        Intent intent = new Intent(MainActivity.this, MqttMessageService.class);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public static PahoMqttClient getPahoMqttClient() {
        return pahoMqttClient;
    }
    public static MqttAndroidClient getMqttAndroidClient(){
        return client;
    }

    public void setTemp(String msg){
        textTemp.setText(msg);
    }
}
