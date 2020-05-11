package com.app.androidkt.mqtt.ui.statistic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.androidkt.mqtt.MainActivity;
import com.app.androidkt.mqtt.R;

public class Statistic extends Fragment {

    RecyclerView recyclerView;
    ListView listView;

    TextView tdmlt, tdmld, tdmbt,tdmbd,tdeld,tdelt;
    TextView wmlt, wmld, wmbt,wmbd,weld,welt;
    TextView mmlt, mmld, mmbt,mmbd,meld,melt;
    TextView atmlt, atmld, atmbt,atmbd,ateld,atelt;
    RelativeLayout day, week, month, all, data;
    LinearLayout dataDATA, weekDATA, monthDATA, allDATA, dayDATA;
    static double totalExtrLight, totalMainLight, totalMusicBox;
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

        totalMainLight = 25;
        totalExtrLight = 0;
        totalMusicBox = 6;

        totalMainLightW = 2165;
        totalExtrLightW = 0;
        totalMusicBoxW = 1236;

        totalMainLightA = 4926;
        totalExtrLightA = 0;
        totalMusicBoxA = 2159;

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

        tdmlt.setText("Total: 60BT Duration:" +  Double.toString(totalMainLight +  (double)MainActivity.getTotalMain()) + " min");
        tdelt.setText("Total: 50BT Duration:" + Double.toString(totalExtrLight + (double)MainActivity.getTotalExtra()) + " min");
        tdmbt.setText("Total: 40BT Duration:" + Double.toString(totalMusicBox + (double)MainActivity.getTotalMusic()) + " min");

        tdmld.setText(Math.round(totalMainLight/60*60) + " Вт");
        tdeld.setText(Math.round(totalExtrLight/60*50) + " Вт");
        tdmbd.setText(Math.round(totalMusicBox/60*40) + " Вт");


        wmlt = (TextView) root.findViewById(R.id.lwmlt);
        wmld = (TextView) root.findViewById(R.id.lwmld);
        wmbt = (TextView) root.findViewById(R.id.lwmbt);
        wmbd = (TextView) root.findViewById(R.id.lwmbd);
        weld = (TextView) root.findViewById(R.id.lweld);
        welt = (TextView) root.findViewById(R.id.lwelt);

        wmlt.setText("Total: 60BT Duration:" + Double.toString((double)totalMainLightW) + " min");
        welt.setText("Total: 50BT Duration:" + Double.toString((double)totalExtrLightW) + " min");
        wmbt.setText("Total: 40BT Duration:" + Double.toString((double)totalMusicBoxW) + " min");

        wmld.setText(Math.round(totalMainLightW/60*60) + " Вт");
        weld.setText(Math.round(totalExtrLightW/60*50) + " Вт");
        wmbd.setText(Math.round(totalMusicBoxW/60*50) + " Вт");

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

        atmlt.setText("Total: 60BT Duration:" + Double.toString((double)totalMainLightA) + " min");
        atelt.setText("Total: 50BT Duration:" + Double.toString((double)totalExtrLightA) + " min");
        atmbt.setText("Total: 40BT Duration:" + Double.toString((double)totalMusicBoxA) + " min");

        atmld.setText(Math.round(totalMainLightA/60*60) + " Вт");
        ateld.setText(Math.round(totalExtrLightA/60*50) + " Вт");
        atmbd.setText(Math.round(totalMusicBoxA/60*50) + " Вт");



//        dayDATA.setVisibility(LinearLayout.GONE);
//        weekDATA.setVisibility(LinearLayout.GONE);
        monthDATA.setVisibility(LinearLayout.GONE);
//        allDATA.setVisibility(LinearLayout.GONE);
        dataDATA.setVisibility(LinearLayout.GONE);

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

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.reload);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalMainLight += (double) MainActivity.getTotalMain();
                totalExtrLight += (double) MainActivity.getTotalExtra();
                totalMusicBox += (double) MainActivity.getTotalMusic();

                totalMainLightW += ((double)MainActivity.getTotalMain());
                totalExtrLightW += ((double)MainActivity.getTotalExtra());
                totalMusicBoxW += ((double)MainActivity.getTotalMusic());

                totalMainLightA += ((double)MainActivity.getTotalMain());
                totalExtrLightA += ((double)MainActivity.getTotalExtra());
                totalMusicBoxA += ((double)MainActivity.getTotalMusic());

                tdmlt.setText("Total: 60BT/ч Duration: " + Double.toString((double)totalMainLight) + " min");
                tdelt.setText("Total: 50BT/ч Duration: " + Double.toString((double)totalExtrLight) + " min");
                tdmbt.setText("Total: 40BT/ч Duration: " + Double.toString((double)totalMusicBox) + " min");

                tdmld.setText(Math.round(totalMainLight/60*60) + " Вт");
                //tdeld.setText(Math.round(totalExtrLight/60*50) + " Вт");
                tdmbd.setText(Math.round(totalMusicBox/60*40) + " Вт");

                wmlt.setText("Total: 60BT/ч Duration:"  + Double.toString((double)totalMainLightW) + " min");
                welt.setText("Total: 50BT/ч Duration: " + Double.toString((double)totalExtrLightW) + " min");
                wmbt.setText("Total: 40BT/ч Duration: " + Double.toString((double)totalMusicBoxW) + " min");

                wmld.setText(Math.round(totalMainLightW/60*60) + " Вт");
                //weld.setText(Math.round(totalExtrLightW/60*50) + " Вт");
                wmbd.setText(Math.round(totalMusicBoxW/60*40) + " Вт");

                atmlt.setText("Total: 60BT/ч Duration: " + Double.toString((double)totalMainLightA) + " min");
                //atelt.setText("Total: 50BT/ч Duration: " + Double.toString((double)totalExtrLightA) + " min");
                atmbt.setText("Total: 40BT/ч Duration: " + Double.toString((double)totalMusicBoxA) + " min");

                atmld.setText(Math.round(totalMainLightA/60*60) + "  Вт");
                //ateld.setText(Math.round(totalExtrLightA/60*50) + "  Вт");
                atmbd.setText(Math.round(totalMusicBoxA/60*40) + "  Вт");

            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
