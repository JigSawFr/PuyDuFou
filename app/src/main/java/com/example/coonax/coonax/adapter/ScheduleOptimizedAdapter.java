package com.example.coonax.coonax.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.coonax.coonax.R;
import com.example.coonax.coonax.model.Schedule;

import java.util.List;

/**
 * Projet       ~~ PuyDuFou ~~
 ****************************************
 * Créé par JigSaw le 18/06/2015 à 02:00
 ****************************************
 *        ___ ______     ___ _       __
 *       / (_) ____/____/   | |     / /
 *  __  / / / / __/ ___/ /| | | /| / /
 * / /_/ / / /_/ (__  ) ___ | |/ |/ /
 * \____/_/\____/____/_/  |_|__/|__/
 *
 */

public class ScheduleOptimizedAdapter extends ScheduleAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Schedule> schedulesList;

    public ScheduleOptimizedAdapter(Activity activity, List<Schedule> schedulesList) {
        super(activity, schedulesList);
        this.activity = activity;
        this.schedulesList = schedulesList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) { inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE); }
        if (convertView == null) { convertView = inflater.inflate(R.layout.list_row_schedules_nocheck, null); }
        convertView = super.getView(position, convertView, parent);
        return convertView;
    }
}
