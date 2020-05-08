package com.app.androidkt.mqtt.ui.statistic;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.androidkt.mqtt.MainActivity;
import com.app.androidkt.mqtt.R;
import com.app.androidkt.mqtt.ui.dummy.DummyContent;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Statistic extends Fragment {

    RecyclerView recyclerView;
    ListView listView;

    TextView tdmlt, tdmld, tdmbt,tdmbd,tdeld,tdelt;
    TextView wmlt, wmld, wmbt,wmbd,weld,welt;
    TextView mmlt, mmld, mmbt,mmbd,meld,melt;
    TextView atmlt, atmld, atmbt,atmbd,ateld,atelt;
    RelativeLayout day, week, month, all, data;
    LinearLayout dataDATA, weekDATA, monthDATA, allDATA, dayDATA;
    static long totalExtrLight, totalMainLight, totalMusicBox;
    static double totalExtrLightW, totalMainLightW, totalMusicBoxW;
    static double totalExtrLightA, totalMainLightA, totalMusicBoxA;
    boolean dayB, weekB, monthB, allB;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_statistic_list, container, false);

//        listView = (ListView) root.findViewById(R.id.listView);
//        ArrayList<DataStatistic> title = new ArrayList<>();
//        DataStatistic q1 = new DataStatistic("Main Light","100КВ","22 min");
//        DataStatistic q2 = new DataStatistic("Main Light","100КВ","22 min");
//        DataStatistic q3 = new DataStatistic("Main Light","100КВ","22 min");
//        title.add(q1);
//        title.add(q2);
//        title.add(q3);
//        DataStatisticListAdapter arrayAdapter = new DataStatisticListAdapter(((MainActivity) getActivity()).getApplicationContext(),R.layout.fragment_statistic_list, title,root);
//
//        listView.setAdapter(arrayAdapter);

        dayB = true;
        weekB = true;
        monthB = true;
        allB = true;

//        dayB = false;
//        weekB = false;
//        monthB = false;
//        allB = false;

        totalMainLight = MainActivity.getTotalMain();
        totalExtrLight = MainActivity.getTotalExtra();
        totalMusicBox = MainActivity.getTotalMusic();

        totalExtrLightW = ((double)MainActivity.getTotalMain())*6.5;
        totalExtrLightW = ((double)MainActivity.getTotalExtra())*5.23;
        totalMusicBoxW = ((double)MainActivity.getTotalMusic())*3.25;

        totalMainLightA = ((double)MainActivity.getTotalMain())*10.35;
        totalExtrLightA = ((double)MainActivity.getTotalExtra())*8.73;
        totalMusicBoxA = ((double)MainActivity.getTotalMusic())*6.5;

        day = (RelativeLayout) root.findViewById(R.id.today);
        week = (RelativeLayout) root.findViewById(R.id.week);
        month = (RelativeLayout) root.findViewById(R.id.month);
        all = (RelativeLayout) root.findViewById(R.id.allday);

        dayDATA = (LinearLayout) root.findViewById(R.id.listtoday);
        weekDATA = (LinearLayout) root.findViewById(R.id.listweak);
        monthDATA = (LinearLayout) root.findViewById(R.id.listmonth);
        allDATA = (LinearLayout) root.findViewById(R.id.listalltime);
        dataDATA = (LinearLayout) root.findViewById(R.id.notdata);



        //TODO: it is very ploho peredelat'
        tdmlt = (TextView) root.findViewById(R.id.ltdmlt);
        tdmld = (TextView) root.findViewById(R.id.ltdmld);
        tdmbt = (TextView) root.findViewById(R.id.ltdmbt);
        tdmbd = (TextView) root.findViewById(R.id.ltdmbd);
        tdelt = (TextView) root.findViewById(R.id.ltdelt);
        tdeld = (TextView) root.findViewById(R.id.ltdeld);

        tdmlt.setText("Total: 60BT Duration:" + Long.toString(totalMainLight) + "min");
        tdelt.setText("Total: 50BT Duration:" + Long.toString(totalExtrLight) + "min");
        tdmbt.setText("Total: 40BT Duration:" + Long.toString(totalMusicBox) + "min");

        tdmld.setText(Double.toString((double)totalMainLight/60*60) + "ВТ/ч");
        tdeld.setText(Double.toString((double)totalExtrLight/60*50) + "ВТ/ч");
        tdmbd.setText(Double.toString((double)totalMusicBox/60*40) + "ВТ/ч");


        wmlt = (TextView) root.findViewById(R.id.lwmlt);
        wmld = (TextView) root.findViewById(R.id.lwmld);
        wmbt = (TextView) root.findViewById(R.id.lwmbt);
        wmbd = (TextView) root.findViewById(R.id.lwmbd);
        weld = (TextView) root.findViewById(R.id.lweld);
        welt = (TextView) root.findViewById(R.id.lwelt);

        wmlt.setText("Total: 60BT Duration:" + Double.toString((double)totalMainLightW) + "min");
        welt.setText("Total: 50BT Duration:" + Double.toString((double)totalExtrLightW) + "min");
        wmbt.setText("Total: 40BT Duration:" + Double.toString((double)totalMusicBoxW) + "min");

        wmld.setText(Double.toString((double)totalMainLightW/60*60) + "ВТ/ч");
        weld.setText(Double.toString((double)totalExtrLightW/60*50) + "ВТ/ч");
        wmbd.setText(Double.toString((double)totalMusicBoxW/60*50) + "ВТ/ч");

        mmlt = (TextView) root.findViewById(R.id.lmmlt);
        mmld = (TextView) root.findViewById(R.id.lmmld);
        mmbt = (TextView) root.findViewById(R.id.lmmbt);
        mmbd = (TextView) root.findViewById(R.id.lmmbd);
        meld = (TextView) root.findViewById(R.id.lmeld);
        melt = (TextView) root.findViewById(R.id.lmelt);


        atmlt = (TextView) root.findViewById(R.id.latmlt);
        atmld = (TextView) root.findViewById(R.id.latmld);
        atmbt = (TextView) root.findViewById(R.id.latmbt);
        atmbd = (TextView) root.findViewById(R.id.latmbd);
        ateld = (TextView) root.findViewById(R.id.lateld);
        atelt = (TextView) root.findViewById(R.id.latelt);

        wmlt.setText("Total: 60BT Duration:" + Double.toString((double)totalMainLightA) + "min");
        welt.setText("Total: 50BT Duration:" + Double.toString((double)totalExtrLightA) + "min");
        wmbt.setText("Total: 40BT Duration:" + Double.toString((double)totalMusicBoxA) + "min");

        wmld.setText(Double.toString((double)totalMainLightA/60*60) + "ВТ/ч");
        weld.setText(Double.toString((double)totalExtrLightA/60*50) + "ВТ/ч");
        wmbd.setText(Double.toString((double)totalMusicBoxA/60*50) + "ВТ/ч");



//        dayDATA.setVisibility(LinearLayout.GONE);
//        weekDATA.setVisibility(LinearLayout.GONE);
//        monthDATA.setVisibility(LinearLayout.GONE);
//        allDATA.setVisibility(LinearLayout.GONE);
//        dataDATA.setVisibility(LinearLayout.GONE);

        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dayB == false){
                    dayDATA.setVisibility(LinearLayout.VISIBLE);
                    dayB = true;
                } else {
                    dayDATA.setVisibility(LinearLayout.GONE);
                    dayB = false;
                }
            }
        });
        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (weekB == false){
                    weekDATA.setVisibility(LinearLayout.VISIBLE);
                    weekB = true;
                } else {
                    weekDATA.setVisibility(LinearLayout.GONE);
                    weekB = false;
                }
            }
        });
        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (monthB == false){
                    dataDATA.setVisibility(LinearLayout.VISIBLE);
                    monthB = true;
                } else {
                    dataDATA.setVisibility(LinearLayout.GONE);
                    monthB = false;
                }
            }
        });
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allB == false){
                    allDATA.setVisibility(LinearLayout.VISIBLE);
                    allB = true;
                } else {
                    allDATA.setVisibility(LinearLayout.GONE);
                    allB = false;
                }
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        totalMainLight = MainActivity.getTotalMain();
        totalExtrLight = MainActivity.getTotalExtra();
        totalMusicBox = MainActivity.getTotalMusic();

        totalExtrLightW = ((double)totalMainLight)*6.5;
        totalExtrLightW = ((double)totalExtrLight)*5.23;
        totalMusicBoxW = ((double)totalMusicBox)*3.25;

        totalMainLightA = ((double)totalMainLight)*10.35;
        totalExtrLightA = ((double)totalExtrLight)*8.73;
        totalMusicBoxA = ((double)totalMusicBox)*6.5;

        tdmlt.setText("Total: 60BT Duration:" + Long.toString(totalMainLight) + "min");
        tdelt.setText("Total: 50BT Duration:" + Long.toString(totalExtrLight) + "min");
        tdmbt.setText("Total: 40BT Duration:" + Long.toString(totalMusicBox) + "min");

        tdmld.setText(Double.toString((double)totalMainLight/60*60) + "ВТ/ч");
        tdeld.setText(Double.toString((double)totalExtrLight/60*50) + "ВТ/ч");
        tdmbd.setText(Double.toString((double)totalMusicBox/60*40) + "ВТ/ч");

        wmlt.setText("Total: 60BT Duration:" + Double.toString((double)totalMainLightW) + "min");
        welt.setText("Total: 50BT Duration:" + Double.toString((double)totalExtrLightW) + "min");
        wmbt.setText("Total: 40BT Duration:" + Double.toString((double)totalMusicBoxW) + "min");

        wmld.setText(Double.toString((double)totalMainLightW/60*60) + "ВТ/ч");
        weld.setText(Double.toString((double)totalExtrLightW/60*50) + "ВТ/ч");
        wmbd.setText(Double.toString((double)totalMusicBoxW/60*40) + "ВТ/ч");

        wmlt.setText("Total: 60BT Duration:" + Double.toString((double)totalMainLightA) + "min");
        welt.setText("Total: 50BT Duration:" + Double.toString((double)totalExtrLightA) + "min");
        wmbt.setText("Total: 40BT Duration:" + Double.toString((double)totalMusicBoxA) + "min");

        wmld.setText(Double.toString((double)totalMainLightA/60*60) + "ВТ/ч");
        weld.setText(Double.toString((double)totalExtrLightA/60*50) + "ВТ/ч");
        wmbd.setText(Double.toString((double)totalMusicBoxA/60*50) + "ВТ/ч");
    }
}
