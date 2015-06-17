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

import android.widget.*;
import com.example.coonax.coonax.R;
import com.example.coonax.coonax.model.Mark;
import com.example.coonax.coonax.model.Restaurant;
import com.example.coonax.coonax.model.Show;
import com.example.coonax.coonax.service.PuyDuFou;
import com.squareup.picasso.Picasso;
import org.parceler.apache.commons.lang.WordUtils;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;

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

public class RestaurantActivity extends Activity {

    Integer mySingleId = null;
    RatingBar mark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        /* ~~ COONAX ## START ~~ */
        ActionBar ab = getActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#9b59b6"));
        ab.setBackgroundDrawable(colorDrawable);
        /* ~~ COONAX ## END ~~ */

        Intent mySingleView = getIntent();
        mark = (RatingBar) findViewById(R.id.rating_restaurant);

        mySingleId = mySingleView.getExtras().getInt("id");
        Log.i("PUYDUFOU", "SHOW_RESTAURANT :: Demande d'informations sur le restaurant portant l'ID n°" + mySingleId);
        refreshRestaurant();

        mark.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
                                              @Override public void onRatingChanged(RatingBar mark, float rating, boolean fromUser){
                                                  //mark.setRating(rating);
                                                  if(fromUser) {
                                                      mark.setIsIndicator(true);
                                                      Integer userRating = Math.round(rating);
                                                      Toast.makeText(getApplicationContext(), "Merci ! Votre note: " + userRating + "/5", Toast.LENGTH_LONG).show();
                                                      markRestaurant(userRating);
                                                      Log.i("PUYDUFOU", "RATING_RESTAURANT :: Notation du restaurant avec la note " + userRating + "/5 (USER ? " + fromUser + ")");
                                                  }
                                              }
                                          }
        );
    }

    private void markRestaurant(int myUserMark) {
        PuyDuFou puyDuFouService = new RestAdapter.Builder()
                .setEndpoint(PuyDuFou.ENDPOINT)
                .setLog(new AndroidLog("retrofit"))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
                .create(PuyDuFou.class);
        try {
            puyDuFouService.restaurantMarkAsync(mySingleId, myUserMark, new Callback<Mark>() {
                @Override
                public void success(Mark myMark, Response response) {
                    Log.i("PUYDUFOU", "RATING_RESTAURANT :: Le restaurant a bien été noté" + myMark.getStatut());
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.w("PUYDUFOU", "RATING_RESTAURANT :: Impossible de noter le restaurant");
                    Log.w("PUYDUFOU", "RATING_RESTAURANT :: " + error);
                            Toast.makeText(getApplicationContext(), "FAIL" + error, Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e) {
            Log.w("PUYDUFOU", "RATING_RESTAURANT :: EXCEPTION: " + e);
        }
    }

    private void refreshRestaurant() {
        PuyDuFou puyDuFouService = new RestAdapter.Builder()
                .setEndpoint(PuyDuFou.ENDPOINT)
                .setLog(new AndroidLog("retrofit"))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
                .create(PuyDuFou.class);
        try {
            puyDuFouService.restaurantAsync(mySingleId, new Callback<Restaurant>() {
                @Override
                public void success(Restaurant myRestaurant, Response response) {
                    Log.i("PUYDUFOU", "SHOW_RESTAURANT :: Le restaurant " + myRestaurant.getName() + " a été réceptionné avec succès !");
                    Log.i("PUYDUFOU", "SHOW_RESTAURANT :: " + myRestaurant.toString());

                    ImageView image = (ImageView) findViewById(R.id.image_restaurant);
                    TextView description = (TextView) findViewById(R.id.text_long_desc_restaurant);
                    RatingBar mark = (RatingBar) findViewById(R.id.rating_restaurant);
                    TextView latitude = (TextView) findViewById(R.id.text_value_latitude_restaurant);
                    TextView longitude = (TextView) findViewById(R.id.text_value_longitude_restaurant);

                    setTitle(WordUtils.capitalize(myRestaurant.getName()));
                    description.setText(myRestaurant.getShortDescription());
                    Picasso.with(getApplicationContext()).load(myRestaurant.getImage()).placeholder(R.drawable.placeholder).fit().centerCrop().into(image);
                    mark.setRating(myRestaurant.getNote().floatValue());
                    latitude.setText(myRestaurant.getLatitude().toString());
                    longitude.setText(myRestaurant.getLongitude().toString());
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.w("PUYDUFOU", "SHOW_RESTAURANT :: Impossible de récupérer le restaurant");
                    Log.w("PUYDUFOU", "SHOW_RESTAURANT :: " + error);
                            Toast.makeText(getApplicationContext(), "FAIL" + error, Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e) {
            Log.w("PUYDUFOU", "SHOW_RESTAURANT :: EXCEPTION: " + e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_restaurant, menu);
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
