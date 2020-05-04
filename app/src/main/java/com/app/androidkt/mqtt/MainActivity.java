package com.app.androidkt.mqtt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.app.androidkt.mqtt.ui.dashboard.DashboardFragment;
import com.app.androidkt.mqtt.ui.event.EventFragment;
import com.app.androidkt.mqtt.ui.manage.ManageFragment;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.UnsupportedEncodingException;

import com.app.androidkt.mqtt.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static PahoMqttClient pahoMqttClient;
    private static MqttAndroidClient client;
    private String TAG = "MainActivity";
    final String LOG_TAG = "BroadcastReceiver";
    final int TASK1_CODE = 1;
    final int TASK2_CODE = 2;
    final int TASK3_CODE = 3;
    public final static int STATUS_START = 100;
    public final static int STATUS_FINISH = 200;
    public final static String PARAM_TIME = "time";
    public final static String PARAM_TASK = "task";
    public final static String PARAM_RESULT = "result";
    public final static String PARAM_STATUS = "status";
    public final static String BROADCAST_ACTION = "BROADCAST_ACTION";

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;

    private AppBarConfiguration mAppBarConfiguration;

    private EditText textMessage,textTopic, subscribeTopic, unSubscribeTopic, textTemp, textTime, textClient;
    private Button publishMessage, subscribe, unSubscribe, reConnection;
    private TextView userName, userStatus;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private DashboardFragment dashboardFragment;
    private ManageFragment manageFragment;
    private EventFragment eventFragment;

    private ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_menu);
        pahoMqttClient = new PahoMqttClient();
        client = pahoMqttClient.getMqttClient(getApplicationContext(), Constants.MQTT_BROKER_URL, Constants.CLIENT_ID);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        user = FirebaseAuth.getInstance().getCurrentUser();

//        textTemp   = (EditText) findViewById(R.id.text_temp);
//        textTime   = (EditText) findViewById(R.id.text_time);
//        textClient = (EditText) findViewById(R.id.text_client);
//
//        textMessage    = (EditText) findViewById(R.id.pub_msg);
//        textTopic      = (EditText) findViewById(R.id.pub_topic);
//        publishMessage = (Button)   findViewById(R.id.pun_button);
//
//        subscribeTopic = (EditText) findViewById(R.id.sub_topic);
//        subscribe      = (Button)   findViewById(R.id.sub_button);
//
//        unSubscribeTopic = (EditText) findViewById(R.id.unsub_topic);
//        unSubscribe      = (Button)   findViewById(R.id.unsub_button);
//
//        reConnection = (Button) findViewById(R.id.reconnection);
//
//        publishMessage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String msg = textMessage.getText().toString().trim();
//                String top = textTopic.getText().toString().trim();
//                if (!msg.isEmpty() || !top.isEmpty()) {
//                    try {
//                        pahoMqttClient.publishMessage(client, msg, 1, top);
//                        Toast.makeText(getApplicationContext(), "\"" + msg + "\" has been sent to " + top, Toast.LENGTH_SHORT).show();
//                    } catch (MqttException e) {
//                        Toast.makeText(getApplicationContext(), "Error "+ e.toString(), Toast.LENGTH_SHORT).show();
//                        e.printStackTrace();
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                        Toast.makeText(getApplicationContext(), "Error "+ e.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(getApplicationContext(), "Message or Topic is empty!", Toast.LENGTH_SHORT).show();
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
//                        Toast.makeText(getApplicationContext(), "Subscribed to " + topic, Toast.LENGTH_SHORT).show();
//                    } catch (MqttException e) {
//                        e.printStackTrace();
//                        Toast.makeText(getApplicationContext(), "Error "+ e.toString(), Toast.LENGTH_SHORT).show();
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
//                        Toast.makeText(getApplicationContext(), "unSubscribed to " + topic, Toast.LENGTH_SHORT).show();
//                    } catch (MqttException e) {
//                        e.printStackTrace();
//                        Toast.makeText(getApplicationContext(), "Error "+ e.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });
//
//        reConnection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                finish();
//            }
//        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        dashboardFragment = new DashboardFragment();
        manageFragment = new ManageFragment();
        eventFragment = new EventFragment();

//        userName = (TextView) header.findViewById(R.id.usernameGlobal);
//        userStatus = (TextView) header.findViewById(R.id.textView);
//
//        userName.setText(user.getDisplayName());

        dashboardFragment = new DashboardFragment();
        fragmentTransaction.add(R.id.myContainer, dashboardFragment);
        fragmentTransaction.commit();


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_main, R.id.nav_gallery, R.id.nav_slideshow,
//                R.id.nav_manage, R.id.nav_share, R.id.nav_send)
//                .setDrawerLayout(drawer)
//                .build();
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //NavigationUI.setupWithNavController(navigationView, navController);

        Intent intent = new Intent(MainActivity.this, MqttMessageService.class);
        startService(intent);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.barmenu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            fragmentTransaction.replace(R.id.myContainer, dashboardFragment);
            fragmentTransaction.commit();


        } else if (id == R.id.nav_manage) {
            fragmentTransaction.replace(R.id.myContainer, manageFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_events) {
            fragmentTransaction.replace(R.id.myContainer, eventFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_share) {


            //mDatabase.child("users").child(user.getUid()).child("username").setValue(user.getDisplayName());
            //mDatabase.child("users").child(user.getUid()).child("studentID").setValue("S3919000");

        } else if (id == R.id.nav_send) {
            //userStatus.setText(mFirebaseAnalytics.);
//            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                    .setDisplayName("Лучший постановщик")
//                    .setPhotoUri(Uri.parse("https://example.com/example.jpg"))
//                    .build();
//
//            user.updateProfile(profileUpdates)
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                Toast.makeText(MainActivity.this, "Work", Toast.LENGTH_LONG).show();
//
//                            }
//                        }
//                    });

        }else if (id == R.id.exit) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
