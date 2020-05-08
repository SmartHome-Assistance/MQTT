package com.app.androidkt.mqtt;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import com.app.androidkt.mqtt.MainActivity;
import com.app.androidkt.mqtt.StartActivity;
import com.app.androidkt.mqtt.ui.dashboard.DashboardFragment;
import com.app.androidkt.mqtt.ui.home.HomeFragment;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MqttMessageService extends Service {

    private static final String TAG = "MqttMessageService";
    static private PahoMqttClient pahoMqttClient;
    private MqttAndroidClient mqttAndroidClient;
    ExecutorService es;
    private boolean musicPlay;

    private NotificationManager mNotificationManager;
    public MqttMessageService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        es = Executors.newFixedThreadPool(4);
        musicPlay = false;

        pahoMqttClient = MainActivity.getPahoMqttClient();
        mqttAndroidClient = MainActivity.getMqttAndroidClient();

        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {

            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                setMessageNotification(s, new String(mqttMessage.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    private void setMessageNotification(@NonNull String topic, @NonNull String msg) {
        if (topic == "music") {
            if (Arrays.equals(msg.toCharArray(), "ON".toCharArray())) {
                musicPlay = true;
            }
            if (Arrays.equals(msg.toCharArray(), "OFF".toCharArray()))
                musicPlay = false;
        }
        MainActivity.getMessage(topic,msg);

//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "MQTT")
//                        .setSmallIcon(R.drawable.ic_message_black_24dp)
//                        .setContentTitle(topic)
//                        .setContentText(msg)
//                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        Intent resultIntent = new Intent(this, MainActivity.class);
//
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addParentStack(MainActivity.class);
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent =
//                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//        mBuilder.setContentIntent(resultPendingIntent);
//
//        NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(1, mBuilder.build());


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, "notify_001");
        Intent ii = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();


        bigText.bigText(topic);
        bigText.setBigContentTitle(msg);
        bigText.setSummaryText("");

        if (musicPlay == true) {
            mBuilder.addAction(R.drawable.ic_media_rew, "Предыдущий", pendingIntent);
            mBuilder.addAction(R.drawable.ic_media_play, "Пауза/Плей", pendingIntent);
            mBuilder.addAction(R.drawable.ic_media_ff, "Следующий", pendingIntent);
        }

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.logo_bw);
        mBuilder.setContentTitle("Your Title");
        mBuilder.setContentText("Your text");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

// === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }
        int id =120;
        if (musicPlay == true) id = 121;
        mNotificationManager.notify(id, mBuilder.build());
    }
}
