package com.app.androidkt.mqtt;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.app.androidkt.mqtt.ui.statistic.Statistic;
import com.app.androidkt.mqtt.ui.statistic.StatisticFragment;
import com.app.androidkt.mqtt.ui.dashboard.DashboardFragment;
import com.app.androidkt.mqtt.ui.event.EventFragment;
import com.app.androidkt.mqtt.ui.manage.ManageFragment;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.File;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.google.firebase.database.ValueEventListener;

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
    private DatabaseReference databaseReference;

    private static DashboardFragment dashboardFragment;
    private ManageFragment manageFragment;
    private EventFragment eventFragment;
    private Statistic statisticFragment;
    private List<String> DATA;
    private boolean con;
    private Switch mainSwitch;
    private static String swi;
    private Timer mTimer;
    private TimerTask mMyTimerTask;

    static LocalDate startMainLight;
    static LocalDate stopMainLight;
    static LocalDate startExtraLight;
    static LocalDate stopExtraLight;
    static LocalDate startMusicBox;
    static LocalDate stopMusicBox;
    static long totalMain, totalExtra, totalMusic;

    private ActionBarDrawerToggle toggle;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_menu);

        pahoMqttClient = new PahoMqttClient();
        client = pahoMqttClient.getMqttClient(getApplicationContext(), Constants.MQTT_BROKER_URL, Constants.CLIENT_ID);
        con = false;
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        totalExtra = 0;
        totalMain = 0;
        totalMusic = 0;

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        user = FirebaseAuth.getInstance().getCurrentUser();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

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
        statisticFragment = new Statistic();

        userName = (TextView) header.findViewById(R.id.usernameGlobal);
        userStatus = (TextView) header.findViewById(R.id.textView);

        //userName.setText(user.getDisplayName());
        //userStatus.setText();

        databaseReference = mDatabase.child("users").child(mAuth.getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("First Name").getValue().toString() + " " +dataSnapshot.child("Second Name").getValue().toString();
                userName.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







//        final ProgressDialog progressDialog=new ProgressDialog(this);
//        progressDialog.setTitle("Loading");
//
//        progressDialog.show();
//        progressDialog.dismiss();



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

        mainSwitch = (Switch) findViewById(R.id.mainlight);
        TimerTask task = new TimerTask() {
            public void run() {
            }
        };
        Timer timer = new Timer("Timer");

        long delay = 1000L;
        timer.schedule(task, delay);

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
//        int connect = 0;
//        int settings = 1;
//        MenuItem item = menu.getItem(connect);
//        SpannableString s = new SpannableString("My red MenuItem");
//        s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
//        item.setTitle(s);



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

        con = client.isConnected();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()){
            case R.id.connectStatus:
                if (con == true) {
                    SpannableString s = new SpannableString("Connected");
                    s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(), 0);
                    item.setTitle(s);
                    con = true;
                    final String[] topics = {"temp", "time", "weather", "client", "mainLight", "extraLight", "song", "voice", "volume", "pause", "mute", "music"};
                    for (int i = 0; i < topics.length; i++ ){
                        try {
                            pahoMqttClient.subscribe(client, topics[i], 1);
                        } catch (MqttException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Error "+ e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    SpannableString s = new SpannableString("DISCONNECT");
                    s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
                    item.setTitle(s);
                    con = false;
                }
                break;
            case R.id.settings:

                break;
            default:
                return super.onOptionsItemSelected(item);

        }

        return true;
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

        } else if (id == R.id.nav_statistic) {
            fragmentTransaction.replace(R.id.myContainer, statisticFragment);
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
    public static void disconnect() throws MqttException {
        client.disconnect();
    }
    public void newConnected(String URL, String ID){
       MqttAndroidClient new_client = pahoMqttClient.getMqttClient(getApplicationContext(), URL, ID);
       client = new_client;
    }

    public void setTemp(String msg){
        textTemp.setText(msg);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void getMessage(String top, String msg){

        if (top == "music") {
            if (Arrays.equals(msg.toCharArray(), "ON".toCharArray()))
                startMusicBox = LocalDate.now();
            if (Arrays.equals(msg.toCharArray(), "OFF".toCharArray())) {
                stopMusicBox = LocalDate.now();
                Duration duration = Duration.between(startMusicBox, stopMusicBox);
                totalMusic += Math.abs(duration.toMinutes());
            }
        }
        if (top == "mainLight") {
            if (Arrays.equals(msg.toCharArray(), "ON".toCharArray()))
                startMainLight = LocalDate.now();
            if (Arrays.equals(msg.toCharArray(), "OFF".toCharArray())) {
                stopMainLight = LocalDate.now();
                Duration duration = Duration.between(startMainLight, stopMainLight);
                totalExtra += Math.abs(duration.toMinutes());
            }
        }
        if (top == "extraLight") {
            if (Arrays.equals(msg.toCharArray(), "ON".toCharArray()))
                startExtraLight = LocalDate.now();
            if (Arrays.equals(msg.toCharArray(), "OFF".toCharArray())) {
                stopExtraLight = LocalDate.now();
                Duration duration = Duration.between(startExtraLight, stopExtraLight);
                totalExtra += Math.abs(duration.toMinutes());
            }
        }
        dashboardFragment.getMessage(top, msg);

    }

    public static long getTotalMusic (){
        return totalMain;
    }

    public static long getTotalExtra (){
        return totalExtra;
    }

    public static long getTotalMain (){
        return totalMain;
    }

}
