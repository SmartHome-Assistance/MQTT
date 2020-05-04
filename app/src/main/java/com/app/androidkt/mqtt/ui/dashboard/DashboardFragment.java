package com.app.androidkt.mqtt.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private Bundle savedState = null;

    static private PahoMqttClient pahoMqttClient;
    private MqttAndroidClient client;

    private EditText textMessage,textTopic, subscribeTopic, unSubscribeTopic;
    private Button publishMessage, subscribe, unSubscribe;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        pahoMqttClient = MainActivity.getPahoMqttClient();
        client = MainActivity.getMqttAndroidClient();
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);


//        textMessage    = (EditText) root.findViewById(R.id.pub_msg);
//        textTopic      = (EditText) root.findViewById(R.id.pub_topic);
//        publishMessage = (Button)   root.findViewById(R.id.pun_button);
//
//        subscribeTopic = (EditText) root.findViewById(R.id.sub_topic);
//        subscribe      = (Button)   root.findViewById(R.id.sub_button);
//
//        unSubscribeTopic = (EditText) root.findViewById(R.id.unsub_topic);
//        unSubscribe      = (Button)   root.findViewById(R.id.unsub_button);
//
//        publishMessage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String msg = textMessage.getText().toString().trim();
//                String top = textTopic.getText().toString().trim();
//                if (!msg.isEmpty() || !top.isEmpty()) {
//                    try {
//                        pahoMqttClient.publishMessage(client, msg, 1, top);
//                        Toast.makeText(((StartActivity) getActivity()).getApplicationContext(), "\"" + msg + "\" has been sent to " + top, Toast.LENGTH_SHORT).show();
//                    } catch (MqttException e) {
//                        Toast.makeText(((StartActivity) getActivity()).getApplicationContext(), "Error "+ e.toString(), Toast.LENGTH_SHORT).show();
//                        e.printStackTrace();
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                        Toast.makeText(((StartActivity) getActivity()).getApplicationContext(), "Error "+ e.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    if (msg.isEmpty()) Toast.makeText(((StartActivity) getActivity()).getApplicationContext(), "Message is empty!", Toast.LENGTH_SHORT).show();
//                    else if (top.isEmpty()) Toast.makeText(((StartActivity) getActivity()).getApplicationContext(), "Topic is empty!", Toast.LENGTH_SHORT).show();
//                    else Toast.makeText(((StartActivity) getActivity()).getApplicationContext(), "Message or Topic is empty!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        subscribe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String topic = subscribeTopic.getText().toString().trim();
//                if (!topic.isEmpty()) {
//                    try {
//                        pahoMqttClient.subscribe(client, topic, 1);
//                        Toast.makeText(((StartActivity) getActivity()).getApplicationContext(), "Subscribed to " + topic, Toast.LENGTH_SHORT).show();
//                    } catch (MqttException e) {
//                        e.printStackTrace();
//                        Toast.makeText(((StartActivity) getActivity()).getApplicationContext(), "Error "+ e.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });
//        unSubscribe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String topic = unSubscribeTopic.getText().toString().trim();
//                if (!topic.isEmpty()) {
//                    try {
//                        pahoMqttClient.unSubscribe(client, topic);
//                        Toast.makeText(((StartActivity) getActivity()).getApplicationContext(), "unSubscribed to " + topic, Toast.LENGTH_SHORT).show();
//                    } catch (MqttException e) {
//                        e.printStackTrace();
//                        Toast.makeText(((StartActivity) getActivity()).getApplicationContext(), "Error "+ e.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });

//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        dashboardViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        //setRetainInstance(true);


//        if(savedInstanceState != null && savedState == null) {
//            savedState = savedInstanceState.getBundle("STAV");
//        }
//        if(savedInstanceState != null) {
//            textMessage.setText(savedInstanceState.getCharSequence("VSTUP"));
//            textTopic.setText(savedInstanceState.getCharSequence("VSTUP"));
//            subscribeTopic.setText(savedInstanceState.getCharSequence("VSTUP"));
//            unSubscribeTopic.setText(savedInstanceState.getCharSequence("VSTUP"));
//        }
//        savedState = null;
        return root;
    }


//
//    @Override
//    public void onDestroyView(){
//        super.onDestroyView();
//        savedState  = saveState(); /* vstup defined here for sure */
//        textMessage = null;
//        textTopic = null;
//        subscribeTopic = null;
//        unSubscribeTopic = null;
//    }
//
//    private Bundle saveState() { /* called either from onDestroyView() or onSaveInstanceState() */
//        Bundle state = new Bundle();
//        state.putCharSequence("VSTUP", textMessage.getText());
//        state.putCharSequence("VSTUP", textTopic.getText());
//        state.putCharSequence("VSTUP", subscribeTopic.getText());
//        state.putCharSequence("VSTUP", unSubscribeTopic.getText());
//        return state;
//    }
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        /* If onDestroyView() is called first, we can use the previously savedState but we can't call saveState() anymore */
//        /* If onSaveInstanceState() is called first, we don't have savedState, so we need to call saveState() */
//        /* => (?:) operator inevitable! */
//        outState.putBundle("STAV", (savedState  != null) ? savedState  : saveState());
//    }
}