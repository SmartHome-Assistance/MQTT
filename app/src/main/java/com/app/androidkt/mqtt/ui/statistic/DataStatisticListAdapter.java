package com.app.androidkt.mqtt.ui.statistic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.androidkt.mqtt.R;

import java.util.ArrayList;

public class DataStatisticListAdapter extends ArrayAdapter<DataStatistic> {

    private static final String TAG = "PersonListAdapter";

    private Context mContext;
    private View mView;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView title;
        TextView discriptoin;
        TextView time;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public DataStatisticListAdapter(Context context, int resource, ArrayList<DataStatistic> objects, View view) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        mView = view;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String title = getItem(position).getTitle();
        String discription = getItem(position).getDiscription();
        String time = getItem(position).getTime();

        //Create the person object with the information
        DataStatistic data = new DataStatistic(title,discription,time);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvTitle = (TextView) mView.findViewById(R.id.titleList);
        TextView tvDiscriptoin = (TextView) mView.findViewById(R.id.discList);
        TextView tvTime = (TextView) mView.findViewById(R.id.timeList);



        tvTitle.setText(data.getTitle());
        tvDiscriptoin.setText(data.getDiscription());
        tvTime.setText(data.getTime());


        return convertView;
    }
}
