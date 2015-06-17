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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.coonax.coonax.R;
import com.example.coonax.coonax.model.Show;
import com.example.coonax.coonax.service.PuyDuFou;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.parceler.apache.commons.lang.WordUtils;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;

/**
 * Projet       ~~ PuyDuFou ~~
 ****************************************
 * Créé par JigSaw le 17/06/2015 à 22:52
 ****************************************
 *        ___ ______     ___ _       __
 *       / (_) ____/____/   | |     / /
 *  __  / / / / __/ ___/ /| | | /| / /
 * / /_/ / / /_/ (__  ) ___ | |/ |/ /
 * \____/_/\____/____/_/  |_|__/|__/
 *
 */

public class MoreInfoShowActivity extends Activity {

    Integer mySingleId = null;
    RatingBar mark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info_show);

        /* ~~ COONAX ## START ~~ */
        ActionBar ab = getActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#34495e"));
        ab.setBackgroundDrawable(colorDrawable);
        /* ~~ COONAX ## END ~~ */

        Intent mySingleView = getIntent();
        mySingleId = mySingleView.getExtras().getInt("id");
        Log.i("PUYDUFOU", "SHOW_MORE_ACTIVITY :: Demande d'informations complémentaires sur le spectacle portant l'ID n°" + mySingleId);
        refreshShow();
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
                    Log.i("PUYDUFOU", "SHOW_MORE_ACTIVITY :: Le spectacle " + myShow.getName() + " a été réceptionné avec succès pour obtenton d'informations complémentaires !");
                    Log.i("PUYDUFOU", "SHOW_MORE_ACTIVITY :: " + myShow.toString());

                    TextView dateCreated = (TextView) findViewById(R.id.text_date_create_show);
                    TextView actorsCount = (TextView) findViewById(R.id.text_value_nbactor_show);
                    RatingBar mark = (RatingBar) findViewById(R.id.rating_more_show);
                    TextView descHistorical = (TextView) findViewById(R.id.text_history_link_show);
                    TextView longitude = (TextView) findViewById(R.id.text_value_longitude_more_show);
                    TextView latitude = (TextView) findViewById(R.id.text_value_latitude_more_show);

                    setTitle(WordUtils.capitalize(myShow.getName()));
                    dateCreated.setText(formatShowCreatedTime(myShow.getCreatedDate()));
                    actorsCount.setText(myShow.getActorsCount().toString());
                    mark.setRating(myShow.getNote().floatValue());
                    descHistorical.setText(myShow.getLongDescription());
                    longitude.setText(myShow.getLongitude().toString());
                    latitude.setText(myShow.getLatitude().toString());
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.w("PUYDUFOU", "SHOW_MORE_ACTIVITY :: Impossible de récupérer les informaions complémentaires du spectacle");
                    Log.w("PUYDUFOU", "SHOW_MORE_ACTIVITY :: " + error);
                            Toast.makeText(getApplicationContext(), "FAIL" + error, Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e) {
            Log.w("PUYDUFOU", "SHOW_MORE_ACTIVITY :: EXCEPTION: " + e);
        }
    }

    private String formatShowCreatedTime(String dateString) {
        DateTimeFormatter myFormatDate = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        DateTime myDate = myFormatDate.parseDateTime(dateString);
        DateTimeFormatter myNewFormatDate = DateTimeFormat.forPattern("MM/yy");
        return myNewFormatDate.print(myDate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_more_info_show, menu);
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
