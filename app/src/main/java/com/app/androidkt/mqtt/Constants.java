package com.app.androidkt.mqtt;

import android.content.Context;

import org.eclipse.paho.android.service.MqttAndroidClient;

public class Constants {

    private static MqttAndroidClient client;
    private static String TAG = "MainActivity";
    private static PahoMqttClient pahoMqttClient;


        public static final String MQTT_BROKER_URL = "tcp://192.168.0.7:1883";
    public static final String PUBLISH_TOPIC = "test";
    public static final String CLIENT_ID = "androidkt";
    public static final String LOGIN_SERVER = "admin";
    public static final String LOGIN_PASSWORD = "123456";

    public static void setClient(Context context){
        pahoMqttClient = new PahoMqttClient();
        client = pahoMqttClient.getMqttClient(context, Constants.MQTT_BROKER_URL, Constants.CLIENT_ID);
    }

    public static PahoMqttClient getPahoMqttClient() {
        return pahoMqttClient;
    }
    public static MqttAndroidClient getMqttAndroidClient(){
        return client;
    }
}

