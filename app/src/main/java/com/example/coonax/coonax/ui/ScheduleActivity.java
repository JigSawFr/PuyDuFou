package com.example.coonax.coonax.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.coonax.coonax.R;
import com.example.coonax.coonax.adapter.GenericAdapterView;
import com.example.coonax.coonax.adapter.ScheduleAdapter;
import com.example.coonax.coonax.adapter.ScheduleButtonAdapter;
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
import java.util.List;

public class ScheduleActivity extends Activity {

    private SwipeRefreshLayout mySwipeRefreshLayout;
    private List<Schedule> myScheduleList = new ArrayList<>();
    private ListView mySchedule;
    private ScheduleAdapter myScheduleAdapter;
    private GenericAdapterView genericAdapterView = new GenericAdapterView();
    private List<Schedule> myPreferedSchedules = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        /* ~~ COONAX ## START ~~ */
        ActionBar ab = getActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#3498db"));
        ab.setBackgroundDrawable(colorDrawable);
        /* ~~ COONAX ## END ~~ */

        final SwipeRefreshLayout mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_schedules_layout);
        mySchedule = (ListView) findViewById(R.id.schedulesList);
        myScheduleAdapter = new ScheduleButtonAdapter(this, myScheduleList, getApplicationContext());
        mySchedule.setAdapter(myScheduleAdapter);

        mySchedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mySingleView = new Intent(getApplicationContext(), ShowActivity.class);
                mySingleView.putExtra("id", (Integer) view.getTag());
                startActivity(mySingleView);
            }
        });

        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshSchedulesList();
                mySwipeRefreshLayout.setRefreshing(false);
            }
        });

        mySwipeRefreshLayout.post(new Runnable() {
                                      @Override
                                      public void run() {
                                          mySwipeRefreshLayout.setRefreshing(true);
                                          refreshSchedulesList();
                                          mySwipeRefreshLayout.setRefreshing(false);
                                      }
                                  }
        );
    }

    private void refreshSchedulesList() {
        PuyDuFou puyDuFouService = new RestAdapter.Builder()
                .setEndpoint(PuyDuFou.ENDPOINT)
                .setLog(new AndroidLog("retrofit"))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
                .create(PuyDuFou.class);
        try {
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
            }
            else
            {
                myDate = new DateTime();
            }
            DateTimeFormatter myDateHoursFmt = DateTimeFormat.forPattern("HH");
            DateTimeFormatter myDateMinutesFmt = DateTimeFormat.forPattern("mm");
            String myDateHours = myDateHoursFmt.print(myDate);
            String myDateMinutes = myDateMinutesFmt.print(myDate);
            Log.i("PUYDUFOU", "SCHEDULES_ACTIVITY :: HEURE => " + myDateHours + " - MINUTE => " + myDateMinutes);

            puyDuFouService.listScheduleAsync(myDateHours, myDateMinutes, new Callback<List<Schedule>>() {
                @Override
                public void success(List<Schedule> schedules, Response response) {
                    Log.i("PUYDUFOU", "SCHEDULES_ACTIVITY :: Les " + schedules.size() + " programmes ont été réceptionnés avec succès !");
                    Toast.makeText(getApplicationContext(), schedules.size() + " programmes disponibles", Toast.LENGTH_SHORT).show();
                    Collections.sort(schedules);
                    myScheduleList.clear();
                    myScheduleList.addAll(myPreferedSchedules);
                    myScheduleList.addAll(schedules);
                    myScheduleAdapter.notifyDataSetChanged();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.w("PUYDUFOU", "SCHEDULES_ACTIVITY :: Impossible de récupérer les programmes");
                    Log.w("PUYDUFOU", "SCHEDULES_ACTIVITY :: " + error);
                    Toast.makeText(getApplicationContext(), "FAIL" + error, Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e) {
            Log.w("PUYDUFOU", "SCHEDULES_ACTIVITY :: EXCEPTION: " + e);
        }
    }

    public void addScheduleToPersonnalsActivities(Schedule mySchedule) {
        Toast.makeText(getApplicationContext(), "Le spectacle " + mySchedule.getShow().getName() + " a été ajouté au planning personnel !", Toast.LENGTH_SHORT).show();
        myPreferedSchedules.add(mySchedule);
        countPreferedSchedules();
        refreshSchedulesList();
    }

    private void countPreferedSchedules() {
        Log.d("PUYDUFOU", "SCHEDULES_ACTIVITY :: L'utilisateur souhaite participer à " + myPreferedSchedules.size() + " séances de spectacles pour le moment !");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
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
