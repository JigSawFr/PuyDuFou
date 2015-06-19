package com.example.coonax.coonax.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.example.coonax.coonax.R;
import com.example.coonax.coonax.model.Schedule;
import com.example.coonax.coonax.service.PuyDuFou;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static com.example.coonax.coonax.adapter.ScheduleAdapter.formatShowTime;

/**
 * Projet       ~~ PuyDuFou ~~
 ****************************************
 * Créé par JigSaw le 17/06/2015 à 02:45
 ****************************************
 *        ___ ______     ___ _       __
 *       / (_) ____/____/   | |     / /
 *  __  / / / / __/ ___/ /| | | /| / /
 * / /_/ / / /_/ (__  ) ___ | |/ |/ /
 * \____/_/\____/____/_/  |_|__/|__/
 *
 */

public class NextScheduleActivity extends Activity {

    private List<Schedule> myPreferedSchedules = new ArrayList<>();
    private Schedule mSchedules = new Schedule();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_schedule);

        /* ~~ COONAX ## START ~~ */
        ActionBar ab = getActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#16a085"));
        ab.setBackgroundDrawable(colorDrawable);
        /* ~~ COONAX ## END ~~ */

        Log.i("PUYDUFOU", "SHOW_NEXT_ACTIVITY :: Demande d'informations sur le la prochaine activité");
        refreshNextActivity();
    }

    private void refreshNextActivity() {

        List<Schedule> schedules = mSchedules.readSchedule(getApplicationContext());
        if(schedules != null) {
            Log.i("PUYDUFOU", "SCHEDULES_PERSO_ACTIVITY :: Les " + schedules.size() + " programmes ont été chargés de la mémoire avec succès !");
            Toast.makeText(getApplicationContext(), schedules.size() + " programmes disponibles", Toast.LENGTH_SHORT).show();
            if (schedules.size() > 0) {
                myPreferedSchedules.addAll(schedules);
            }
        }

        DateTime myDate;

        if(!myPreferedSchedules.isEmpty())
        {
            Schedule myLastSchedule = myPreferedSchedules.get(myPreferedSchedules.size() - 1);
            Log.i("PUYDUFOU", "SCHEDULES_ACTIVITY :: LATEST ACTIVITY IN USER CHOICE: startDate => " + myLastSchedule.getStartTime());
            DateTimeFormatter parser = ISODateTimeFormat.dateTimeParser();
            myDate = parser.parseDateTime(myLastSchedule.getStartTime());
            myDate = myDate.withZone(DateTimeZone.forID("Europe/Paris"));
            myDate = myDate.plusMinutes(myLastSchedule.getShow().getLenght());
            myDate = myDate.plusMinutes(30);

            DateTimeFormatter myDateHoursFmt = DateTimeFormat.forPattern("HH");
            DateTimeFormatter myDateMinutesFmt = DateTimeFormat.forPattern("mm");
            String myDateHours = myDateHoursFmt.print(myDate);
            String myDateMinutes = myDateMinutesFmt.print(myDate);
            Log.i("PUYDUFOU", "SCHEDULES_ACTIVITY :: HEURE => " + myDateHours + " - MINUTE => " + myDateMinutes);

            Iterator<Schedule> ScheduleIterator = myPreferedSchedules.iterator();
            while (ScheduleIterator.hasNext()) {
                Schedule mySchedule = ScheduleIterator.next();
                DateTimeFormatter parse = ISODateTimeFormat.dateTimeParser();
                myDate = parse.parseDateTime(mySchedule.getStartTime());
                myDate = myDate.withZone(DateTimeZone.forID("Europe/Paris"));
                if(myDate.isBeforeNow()) {
                    ScheduleIterator.remove();
                }
            }
            Collections.sort(myPreferedSchedules);
            TextView name = (TextView) findViewById(R.id.text_next_activity_my_schedule);
            TextView desc = (TextView) findViewById(R.id.desc_value_next_activity_my_schedule);
            TextView time = (TextView) findViewById(R.id.text_next_hour_activity_my_schedule);

            if(myPreferedSchedules.size() > 0) {
                Schedule myVeryLastSchedule = myPreferedSchedules.get(0);
                name.setText(myVeryLastSchedule.getShow().getName());
                desc.setText(myVeryLastSchedule.getShow().getLongDescription());
                time.setText(formatShowTime(myVeryLastSchedule.getStartTime(), "HH:mm"));
            }

        }

        PuyDuFou puyDuFouService = new RestAdapter.Builder()
                .setEndpoint(PuyDuFou.ENDPOINT)
                .setLog(new AndroidLog("retrofit"))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
                .create(PuyDuFou.class);
        try {
            puyDuFouService.listOptimizedScheduleAsync("09", "00", "30", new Callback<List<Schedule>>() {
                @Override
                public void success(List<Schedule> myNextActivities, Response response) {
                    Schedule myNextActivity = myNextActivities.get(0);
                    Log.i("PUYDUFOU", "SHOW_NEXT_ACTIVITY :: La prochaine activité a été réceptionnée avec succès !");

                    TextView name = (TextView) findViewById(R.id.text_next_activity);
                    TextView desc = (TextView) findViewById(R.id.desc_next_activity);
                    TextView time = (TextView) findViewById(R.id.time_next_activity);

                    name.setText(myNextActivity.getShow().getName());
                    desc.setText(myNextActivity.getShow().getLongDescription());
                    time.setText(formatShowTime(myNextActivity.getStartTime(), "HH:mm"));
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.w("PUYDUFOU", "SHOW_NEXT_ACTIVITY :: Impossible de récupérer la prochaine activité");
                    Log.w("PUYDUFOU", "SHOW_NEXT_ACTIVITY :: " + error);
                    Toast.makeText(getApplicationContext(), "FAIL" + error, Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e) {
            Log.w("PUYDUFOU", "SHOW_NEXT_ACTIVITY :: EXCEPTION: " + e);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_next_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
