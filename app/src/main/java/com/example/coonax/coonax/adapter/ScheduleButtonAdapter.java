package com.example.coonax.coonax.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.coonax.coonax.R;
import com.example.coonax.coonax.model.Schedule;
import com.example.coonax.coonax.ui.ScheduleActivity;

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

public class ScheduleButtonAdapter extends ScheduleAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Schedule> schedulesList;
    protected Context context;

    public ScheduleButtonAdapter(Activity activity, List<Schedule> schedulesList, Context context) {
        super(activity, schedulesList, null);
        this.activity = activity;
        this.schedulesList = schedulesList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) { inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE); }
        if (convertView == null) { convertView = inflater.inflate(R.layout.list_row_schedules, null); }
        convertView = super.getView(position, convertView, parent);

        Button buttonAdd = (Button) convertView.findViewById(R.id.button_add_new_schedule);
        if(super.mySchedule.isChoosen()) {
            buttonAdd.setBackgroundResource(R.drawable.button_add_schedule);
        }

        buttonAdd.setTag(super.mySchedule);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Schedule mySchedule = (Schedule) view.getTag();
                Log.d("PUYDUFOU", "SCHEDULES_ADAPTER :: Ajout du spectacle : " + mySchedule.getShow().getName() + " (ID=" + mySchedule.getShow().getId() + ") avec la séance de " + formatShowTime(mySchedule.getStartTime(), "HH:mm") + "(ID=" + mySchedule.getId() + ")");
                mySchedule.setIsChoosen(true);
                ((ScheduleActivity) view.getContext()).addScheduleToPersonnalsActivities(mySchedule);
            }

        });
        return convertView;
    }
}
