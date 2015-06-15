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
import android.widget.Button;

import android.widget.ListView;
import android.widget.Toast;
import com.example.coonax.coonax.R;
import com.example.coonax.coonax.adapter.GenericAdapterView;
import com.example.coonax.coonax.adapter.ShowsAdapter;
import com.example.coonax.coonax.model.Shop;
import com.example.coonax.coonax.model.Show;
import com.example.coonax.coonax.service.PuyDuFou;
import retrofit.Callback;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class ShowsActivity extends Activity {

    /* ~~ JIGSAW ## START ~~ */
    private List<Show> myShowsList = new ArrayList<>();
    private ListView myShows;
    private ShowsAdapter myShowsAdapter;
    private GenericAdapterView genericAdapterView = new GenericAdapterView();
    /* ~~ JIGSAW ## END ~~ */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shows);

        ActionBar ab = getActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#34495e"));
        ab.setBackgroundDrawable(colorDrawable);

        /* ~~ JIGSAW ## START ~~ */
        myShows = (ListView) findViewById(R.id.showsList);
        myShowsAdapter = new ShowsAdapter(this, myShowsList);
        myShows.setAdapter(myShowsAdapter);
        myShows.setOnItemClickListener(null);

        PuyDuFou puyDuFouService = new RestAdapter.Builder()
                .setEndpoint(PuyDuFou.ENDPOINT)
                .setLog(new AndroidLog("retrofit"))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
                .create(PuyDuFou.class);
        try {
            puyDuFouService.listShowAsync(new Callback<List<Show>>() {
                @Override
                public void success(List<Show> shows, Response response) {
                    Log.i("RESTFULL", "NICE ON A LES SPECTACLES !!!! NB=" + shows.size());
                    myShowsList.addAll(shows);
                    myShowsAdapter.notifyDataSetChanged();
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getApplicationContext(), "FAIL" + error, Toast.LENGTH_LONG).show();
                    Log.e("RESTFULL", "FAIL: " + error);
                }
            });
        }
        catch (Exception e) {
            Log.e("ERREUR !", "FAIL: " + e);
        }
        /* ~~ JIGSAW ## END ~~ */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shows, menu);
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
