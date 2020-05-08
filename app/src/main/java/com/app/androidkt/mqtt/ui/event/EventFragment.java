package com.app.androidkt.mqtt.ui.event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.app.androidkt.mqtt.R;

public class EventFragment extends Fragment {

    private EventViewModel eventViewModel;
    Switch aSwitch1, aSwitch2;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        eventViewModel =
                ViewModelProviders.of(this).get(EventViewModel.class);
        View root = inflater.inflate(R.layout.fragment_event, container, false);

        aSwitch1 = (Switch) root.findViewById(R.id.switch1);
        aSwitch2 = (Switch) root.findViewById(R.id.switch2);

        aSwitch1.setChecked(true);
        aSwitch2.setChecked(true);

        return root;
    }
}