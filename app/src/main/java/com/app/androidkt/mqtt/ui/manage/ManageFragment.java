package com.app.androidkt.mqtt.ui.manage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.androidkt.mqtt.Constants;
import com.app.androidkt.mqtt.MainActivity;
import com.app.androidkt.mqtt.PahoMqttClient;
import com.app.androidkt.mqtt.R;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.UnsupportedEncodingException;

public class ManageFragment extends Fragment {

    private Constants constants;

    private ManageViewModel manageViewModel;
    private static EditText textTemp;

    static private PahoMqttClient pahoMqttClient;
    private MqttAndroidClient client;

    private EditText textMessage,textTopic, subscribeTopic, unSubscribeTopic, ipAddres, clientID, loginServer, passwordServer;
    private RelativeLayout publishMessage, subscribe, unSubscribe, reconection;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_manage, container, false);

        pahoMqttClient = MainActivity.getPahoMqttClient();
        client = MainActivity.getMqttAndroidClient();


        //SETTINGS CLIENT

        ipAddres    = (EditText) root.findViewById(R.id.id_server);
        clientID    = (EditText) root.findViewById(R.id.clientID);
        loginServer    = (EditText) root.findViewById(R.id.id_login);
        passwordServer    = (EditText) root.findViewById(R.id.id_password);

        reconection = (RelativeLayout) root.findViewById(R.id.mRecBtn);

        ipAddres.setText(constants.MQTT_BROKER_URL);
        clientID.setText(constants.CLIENT_ID);
        loginServer.setText(constants.LOGIN_SERVER);
        passwordServer.setText(constants.LOGIN_PASSWORD);

        reconection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = ipAddres.getText().toString();
                String id = clientID.getText().toString();
                if (ip != constants.MQTT_BROKER_URL || id != constants.CLIENT_ID){



                } else {


                }
            }
        });

        //TOPIC SETTINGS
        textMessage    = (EditText) root.findViewById(R.id.pub_msg);
        textTopic      = (EditText) root.findViewById(R.id.pub_topic);
        publishMessage = (RelativeLayout)  root.findViewById(R.id.pub_button);

        subscribeTopic = (EditText) root.findViewById(R.id.sub_topic);
        subscribe      = (RelativeLayout)   root.findViewById(R.id.sub_button);

        unSubscribeTopic = (EditText) root.findViewById(R.id.unsub_topic);
        unSubscribe      = (RelativeLayout)   root.findViewById(R.id.unsub_button);

        publishMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = textMessage.getText().toString().trim();
                String top = textTopic.getText().toString().trim();
                if (!msg.isEmpty() || !top.isEmpty()) {
                    try {
                        pahoMqttClient.publishMessage(client, msg, 1, top);
                        Toast.makeText(((MainActivity) getActivity()).getApplicationContext(), "\"" + msg + "\" has been sent to " + top, Toast.LENGTH_SHORT).show();
                    } catch (MqttException e) {
                        Toast.makeText(((MainActivity) getActivity()).getApplicationContext(), "Error "+ e.toString(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        Toast.makeText(((MainActivity) getActivity()).getApplicationContext(), "Error "+ e.toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (msg.isEmpty()) Toast.makeText(((MainActivity) getActivity()).getApplicationContext(), "Message is empty!", Toast.LENGTH_SHORT).show();
                    else if (top.isEmpty()) Toast.makeText(((MainActivity) getActivity()).getApplicationContext(), "Topic is empty!", Toast.LENGTH_SHORT).show();
                    else Toast.makeText(((MainActivity) getActivity()).getApplicationContext(), "Message or Topic is empty!", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(((MainActivity) getActivity()).getApplicationContext(), "Subscribed to " + topic, Toast.LENGTH_SHORT).show();
                    } catch (MqttException e) {
                        e.printStackTrace();
                        Toast.makeText(((MainActivity) getActivity()).getApplicationContext(), "Error "+ e.toString(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(((MainActivity) getActivity()).getApplicationContext(), "unSubscribed to " + topic, Toast.LENGTH_SHORT).show();
                    } catch (MqttException e) {
                        e.printStackTrace();
                        Toast.makeText(((MainActivity) getActivity()).getApplicationContext(), "Error "+ e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        dashboardViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });


        return root;
    }
    public static void setTemp(String s){
        textTemp.setText(s);
    }

}