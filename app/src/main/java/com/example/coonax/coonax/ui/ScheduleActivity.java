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
import android.widget.*;
import com.example.coonax.coonax.R;
import com.example.coonax.coonax.adapter.GenericAdapterView;
import com.example.coonax.coonax.adapter.ScheduleAdapter;
import com.example.coonax.coonax.model.Schedule;
import com.example.coonax.coonax.service.PuyDuFou;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);


        /* ~~ COONAX ## START ~~ */
        //Affichage de la ActionBar coloré
        ActionBar ab = getActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#3498db"));
        ab.setBackgroundDrawable(colorDrawable);

        //Bouton d'accès à l'activité BestScheduleActivity
        /*Button switchButtonBestSchedule = (Button) findViewById(R.id.button_best_schedule);
        switchButtonBestSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleActivity.this, BestScheduleActivity.class);
                startActivity(intent);
            }
        });*/

        //Bouton d'accès à l'activité MyScheduleActivity
       /* Button switchButtonAddSchedule = (Button) findViewById(R.id.button_add_schedule);
        switchButtonAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleActivity.this, MyScheduleActivity.class);
                startActivity(intent);
            }
        });
        /* ~~ COONAX ## END ~~ */

        final SwipeRefreshLayout mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_schedules_layout);
        mySchedule = (ListView) findViewById(R.id.schedulesList);
        myScheduleAdapter = new ScheduleAdapter(this, myScheduleList);
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
            puyDuFouService.listScheduleAsync("09", "00", new Callback<List<Schedule>>() {
                @Override
                public void success(List<Schedule> schedules, Response response) {
                    Log.i("PUYDUFOU", "SCHEDULES_ACTIVITY :: Les " + schedules.size() + " programmes ont été réceptionnés avec succès !");
                    Toast.makeText(getApplicationContext(), schedules.size() + " programmes disponibles", Toast.LENGTH_LONG).show();
                    Collections.sort(schedules);
                    myScheduleList.clear();
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
