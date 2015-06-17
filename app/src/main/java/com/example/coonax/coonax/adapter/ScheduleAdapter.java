package com.example.coonax.coonax.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.coonax.coonax.R;
import com.example.coonax.coonax.model.Schedule;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.parceler.apache.commons.lang.WordUtils;

import java.util.List;

/**
 * Projet       ~~ PuyDuFou ~~
 ****************************************
 * Créé par JigSaw le 17/06/2015 à 15:48
 ****************************************
 *        ___ ______     ___ _       __
 *       / (_) ____/____/   | |     / /
 *  __  / / / / __/ ___/ /| | | /| / /
 * / /_/ / / /_/ (__  ) ___ | |/ |/ /
 * \____/_/\____/____/_/  |_|__/|__/
 *
 */

public class ScheduleAdapter extends BaseAdapter {


    private Activity activity;
    private LayoutInflater inflater;
    private List<Schedule> schedulesList;

    public ScheduleAdapter(Activity activity, List<Schedule> schedulesList) {
        this.activity = activity;
        this.schedulesList = schedulesList;
    }

    @Override
    public int getCount() {
        return schedulesList.size();
    }

    @Override
    public Object getItem(int location) {
        return schedulesList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null) { inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE); }
        if (convertView == null) { convertView = inflater.inflate(R.layout.list_row_schedules, null); }

        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView description = (TextView) convertView.findViewById(R.id.description);
        TextView time = (TextView) convertView.findViewById(R.id.time);

        Schedule mySchedule = schedulesList.get(position);
        title.setText(WordUtils.capitalize(mySchedule.getShow().getName()));
        description.setText(mySchedule.getShow().getLongDescription());
        convertView.setTag(new Integer(Integer.valueOf(mySchedule.getShow().getId())));
        time.setText(formatShowTime(mySchedule.getStartTime()));

        return convertView;
    }

    private String formatShowTime(String dateString) {
        /*Date date = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        try {
            date = format.parse(dateString);
            Log.i("PUYDUFOU", "SCHEDULES_ACTIVITY :: Conversion de la date reçue -> " + dateString + " en " + date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "00";*/
        //DateTime myDate = new DateTime(dateString);
        DateTimeFormatter myFormatDate = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        DateTime myDate = myFormatDate.parseDateTime(dateString);
        DateTimeFormatter myNewFormatDate = DateTimeFormat.forPattern("HH:mm");
        return myNewFormatDate.print(myDate);
        //return myDate.getHourOfDay() + "h" + myDate.getMinuteOfHour();
    }

}
