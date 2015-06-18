package com.example.coonax.coonax.ui;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.example.coonax.coonax.R;
import com.example.coonax.coonax.adapter.ScheduleAdapter;
import com.example.coonax.coonax.model.Mark;
import com.example.coonax.coonax.model.Schedule;
import com.example.coonax.coonax.model.Show;
import com.example.coonax.coonax.service.PuyDuFou;
import com.squareup.picasso.Picasso;
import org.parceler.apache.commons.lang.WordUtils;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Projet       ~~ PuyDuFou ~~
 ****************************************
 * Créé par JigSaw le 16/06/2015 à 01:57
 ****************************************
 *        ___ ______     ___ _       __
 *       / (_) ____/____/   | |     / /
 *  __  / / / / __/ ___/ /| | | /| / /
 * / /_/ / / /_/ (__  ) ___ | |/ |/ /
 * \____/_/\____/____/_/  |_|__/|__/
 *
 */

public class ShowActivity extends Activity {

    Integer mySingleId = null;
    RatingBar mark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        /* ~~ COONAX ## START ~~ */
        ActionBar ab = getActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#34495e"));
        ab.setBackgroundDrawable(colorDrawable);

        Button switchButtonMoreInfoShow = (Button) findViewById(R.id.button_moreinfo_show);
        switchButtonMoreInfoShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* ~~ JIGSAW ## START ~~ */
                Intent mySingleView = new Intent(getApplicationContext(), MoreInfoShowActivity.class);
                mySingleView.putExtra("id", mySingleId);
                startActivity(mySingleView);
                /* ~~ JIGSAW ## END ~~ */
            }
        });
        /* ~~ COONAX ## END ~~ */

        Intent mySingleView = getIntent();
        mark = (RatingBar) findViewById(R.id.rating_show);

        mySingleId = mySingleView.getExtras().getInt("id");
        Log.i("PUYDUFOU", "SHOW_ACTIVITY :: Demande d'informations sur le spectacle portant l'ID n°" + mySingleId);
        refreshShow();

        mark.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
               @Override public void onRatingChanged(RatingBar mark, float rating, boolean fromUser){
                   //mark.setRating(rating);
                   if(fromUser) {
                       mark.setIsIndicator(true);
                       Integer userRating = Math.round(rating);
                       Toast.makeText(getApplicationContext(), "Merci ! Votre note: " + userRating + "/5", Toast.LENGTH_LONG).show();
                       markShow(userRating);
                       Log.i("PUYDUFOU", "SHOW_ACTIVITY :: Notation de l'activité avec la note " + userRating + "/5 (USER ? " + fromUser + ")");
                   }
               }
           }
        );
    }

    private void markShow(int myUserMark) {
        PuyDuFou puyDuFouService = new RestAdapter.Builder()
                .setEndpoint(PuyDuFou.ENDPOINT)
                .setLog(new AndroidLog("retrofit"))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
                .create(PuyDuFou.class);
        try {
            puyDuFouService.showMarkAsync(mySingleId, myUserMark, new Callback<Mark>() {
                @Override
                public void success(Mark myMark, Response response) {
                    Log.i("PUYDUFOU", "SHOW_ACTIVITY :: Le spectacle a bien été noté, nouvelle moyenne de " + myMark.getAverage() + "/5");
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.w("PUYDUFOU", "SHOW_ACTIVITY :: Impossible de noter le spectacle");
                    Log.w("PUYDUFOU", "SHOW_ACTIVITY :: " + error);
                            Toast.makeText(getApplicationContext(), "FAIL" + error, Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e) {
            Log.w("PUYDUFOU", "SHOW_ACTIVITY :: EXCEPTION: " + e);
        }
    }

    private void refreshShow() {
        PuyDuFou puyDuFouService = new RestAdapter.Builder()
                .setEndpoint(PuyDuFou.ENDPOINT)
                .setLog(new AndroidLog("retrofit"))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
                .create(PuyDuFou.class);
        try {
            puyDuFouService.showAsync(mySingleId, new Callback<Show>() {
                @Override
                public void success(Show myShow, Response response) {
                    Log.i("PUYDUFOU", "SHOW_ACTIVITY :: Le spectacle " + myShow.getName() + " a été réceptionné avec succès !");
                    Log.i("PUYDUFOU", "SHOW_ACTIVITY :: " + myShow.toString());

                    ImageView image = (ImageView) findViewById(R.id.image_show);
                    TextView description = (TextView) findViewById(R.id.text_long_desc_show);
                    TextView time = (TextView) findViewById(R.id.text_time_show);
                    RatingBar mark = (RatingBar) findViewById(R.id.rating_show);

                    setTitle(WordUtils.capitalize(myShow.getName()));
                    description.setText(myShow.getLongDescription());
                    time.setText(myShow.getLenght().toString() + " min.");
                    Picasso.with(getApplicationContext()).load(myShow.getImage()).placeholder(R.drawable.placeholder).fit().centerCrop().into(image);
                    mark.setRating(myShow.getNote().floatValue());

                    refreshSchedulesShow();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.w("PUYDUFOU", "SHOW_ACTIVITY :: Impossible de récupérer le spectacle");
                    Log.w("PUYDUFOU", "SHOW_ACTIVITY :: " + error);
                            Toast.makeText(getApplicationContext(), "FAIL" + error, Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e) {
            Log.w("PUYDUFOU", "SHOW_ACTIVITY :: EXCEPTION: " + e);
        }
    }

    private void refreshSchedulesShow() {
        PuyDuFou puyDuFouService = new RestAdapter.Builder()
                .setEndpoint(PuyDuFou.ENDPOINT)
                .setLog(new AndroidLog("retrofit"))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
                .create(PuyDuFou.class);
        try {
            puyDuFouService.listScheduleShowAsync(mySingleId, new Callback<List<Schedule>>() {

                @Override
                public void success(List<Schedule> schedules, Response response) {
                    Log.i("PUYDUFOU", "SHOW_ACTIVITY :: Les programmations du spectacle ont été réceptionnées avec succès !");
                    Log.i("PUYDUFOU", "SHOW_ACTIVITY :: " + schedules.toString());

                    TextView times = (TextView) findViewById(R.id.text_value_times_show);

                    Iterator<Schedule> ScheduleIterator = schedules.iterator();
                    ArrayList<String> timesShow = new ArrayList<String>();

                    while (ScheduleIterator.hasNext()) {
                        Schedule myScheduleShow = ScheduleIterator.next();
                        timesShow.add(ScheduleAdapter.formatShowTime((String) myScheduleShow.getStartTime(), "HH:mm"));
                    }

                    String descStr = "";
                    for (String str : timesShow) {
                        descStr += str + " ● ";
                    }
                    descStr = descStr.length() > 0 ? descStr.substring(0,
                            descStr.length() - 2) : descStr;
                    times.setText(descStr);
                    times.setSelected(true);

                }

                @Override
                public void failure(RetrofitError error) {
                    Log.w("PUYDUFOU", "SHOW_ACTIVITY :: Impossible de récupérer le spectacle");
                    Log.w("PUYDUFOU", "SHOW_ACTIVITY :: " + error);
                    Toast.makeText(getApplicationContext(), "FAIL" + error, Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e) {
            Log.w("PUYDUFOU", "SHOW_ACTIVITY :: EXCEPTION: " + e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show, menu);
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
