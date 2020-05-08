package com.app.androidkt.mqtt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.*;

public class RegistrationActivity extends AppCompatActivity {

    private EditText firstName;//firstname
    private EditText secondName;//secondname
    private EditText userName;//username
    private EditText birthDay;//birthday
    private EditText ipaddress;//ip address server
    private EditText loginSr;//login on Server
    private EditText pswSR;//password on Server
    private EditText numberPhone;//number
    private EditText mEmailField;//mEmailField
    private EditText mPasswordField;//mPasswordField
    private EditText passwordСonfirm;//passwordСonfirm
    private EditText passwordConfirm;//password_confirm
    private RelativeLayout mRegBtn;
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        firstName       = (EditText) findViewById(R.id.firstname);
        secondName      = (EditText) findViewById(R.id.secondname);
        userName        = (EditText) findViewById(R.id.username);
        birthDay        = (EditText) findViewById(R.id.birthday);
        ipaddress       = (EditText) findViewById(R.id.ipaddress);
        loginSr         = (EditText) findViewById(R.id.login_server);
        pswSR           = (EditText) findViewById(R.id.password_server);
        numberPhone     = (EditText) findViewById(R.id.number);
        passwordConfirm = (EditText) findViewById(R.id.password_confirm);
        mPasswordField  = (EditText) findViewById(R.id.mPasswordField);
        passwordСonfirm  = (EditText) findViewById(R.id.password_confirm);
        mEmailField     = (EditText) findViewById(R.id.mEmailField);
        mRegBtn = (RelativeLayout) findViewById(R.id.mRegBtn);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    LoginActivity.LoginAct.finish();
                    mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("First Name").setValue(firstName.getText().toString());
                    mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Second Name").setValue(secondName.getText().toString());
                    mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("User Name").setValue(userName.getText().toString());
                    mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("BirthDay").setValue(birthDay.getText().toString());
                    mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("IP address").setValue(ipaddress.getText().toString());
                    mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Login server").setValue(loginSr.getText().toString());
                    mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Password server").setValue(pswSR.getText().toString());
                    mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Number Phone").setValue(numberPhone.getText().toString());
                    mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Email").setValue(mEmailField.getText().toString());
                    mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Password").setValue(mPasswordField.getText().toString());
                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                    finish();
                }
            }
        };
        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignUp();
            }
        });
    }

    private void startSignUp(){
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        String passwordConfirm = passwordСonfirm.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || password.length() < 5 || email.indexOf('@') == -1 ){
            if (password.length() < 5) Toast.makeText(RegistrationActivity.this, "Password is so short", Toast.LENGTH_LONG).show();
            else if (email.indexOf('@') == -1) Toast.makeText(RegistrationActivity.this, "Email is not correct", Toast.LENGTH_LONG).show();
            //else if (passwordConfirm != password) Toast.makeText(RegistrationActivity.this, "Passwords are not the same", Toast.LENGTH_LONG).show();
            else if (TextUtils.isEmpty(email)) Toast.makeText(RegistrationActivity.this, "Email is empty", Toast.LENGTH_LONG).show();
            else if (TextUtils.isEmpty(password)) Toast.makeText(RegistrationActivity.this, "Passwords is empty", Toast.LENGTH_LONG).show();
            else  Toast.makeText(RegistrationActivity.this, "I don't know!", Toast.LENGTH_LONG).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(RegistrationActivity.this, "Sign Up Problem", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(RegistrationActivity.this, "Sign Up Success", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegistrationActivity.this, "Please, check your email box", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

}
