package com.app.androidkt.mqtt.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;

import com.app.androidkt.mqtt.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private static EditText textTemp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_manage, container, false);

//        final TextView textView = root.findViewById(R.id.text_home);
//        textTemp = root.findViewById(R.id.text_temp);
//
//        homeViewModel.getText().observe(this, new Observer<String>() {
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