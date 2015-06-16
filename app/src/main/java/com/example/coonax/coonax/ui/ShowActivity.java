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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.coonax.coonax.R;
import com.example.coonax.coonax.app.AppController;
import com.example.coonax.coonax.model.Show;
import com.example.coonax.coonax.service.PuyDuFou;
import com.squareup.picasso.Picasso;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        /* ~~ COONAX ## START ~~ */
        ActionBar ab = getActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#34495e"));
        ab.setBackgroundDrawable(colorDrawable);
        /* ~~ COONAX ## END ~~ */

        Intent mySingleView = getIntent();
        mySingleId = mySingleView.getExtras().getInt("id");
        Log.i("PUYDUFOU", "SHOW_ACTIVITY :: Demande d'informations sur le spectacle portant l'ID n°" + mySingleId);

        /*final SwipeRefreshLayout mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_show_layout);

        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshShow();
                mySwipeRefreshLayout.setRefreshing(false);
            }
        });

        mySwipeRefreshLayout.post(new Runnable() {
                                      @Override
                                      public void run() {
                                          mySwipeRefreshLayout.setRefreshing(true);
                                          refreshShow();
                                          mySwipeRefreshLayout.setRefreshing(false);
                                      }
                                  }
        );*/
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
                    Log.i("PUYDUFOU", "Le spectacle " + myShow.getName() + " a été réceptionné avec succès !");
                    Log.i("PUYDUFOU", myShow.toString());

                    //ImageLoader imageLoader =  AppController.getInstance().getImageLoader();

                    //NetworkImageView image = (NetworkImageView) findViewById(R.id.image_show);
                    ImageView image = (ImageView) findViewById(R.id.image_show);
                    TextView description = (TextView) findViewById(R.id.text_long_desc_show);
                    TextView time = (TextView) findViewById(R.id.text_time_show);

                    //image.setImageUrl(myShow.getImage(), imageLoader);
                    description.setText(myShow.getLongDescription());
                    time.setText(myShow.getLenght().toString() + " min.");
                    Picasso.with(getApplicationContext()).load(myShow.getImage()).placeholder(R.drawable.placeholder).fit().centerCrop().into(image);
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.w("PUYDUFOU", "Impossible de récupérer le spectacle");
                    Log.w("PUYDUFOU", error);
                    Toast.makeText(getApplicationContext(), "FAIL" + error, Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e) {
            Log.w("PUYDUFOU", "EXCEPTION: " + e);
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
