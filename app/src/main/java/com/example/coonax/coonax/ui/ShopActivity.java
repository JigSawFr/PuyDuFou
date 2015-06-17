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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.coonax.coonax.R;
import com.example.coonax.coonax.model.Mark;
import com.example.coonax.coonax.model.Shop;
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
 * Créé par JigSaw le 17/06/2015 à 04:07
 ****************************************
 *        ___ ______     ___ _       __
 *       / (_) ____/____/   | |     / /
 *  __  / / / / __/ ___/ /| | | /| / /
 * / /_/ / / /_/ (__  ) ___ | |/ |/ /
 * \____/_/\____/____/_/  |_|__/|__/
 *
 */

public class ShopActivity extends Activity {

    Integer mySingleId = null;
    RatingBar mark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        /* ~~ COONAX ## START ~~ */
        ActionBar ab = getActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#3498db"));
        ab.setBackgroundDrawable(colorDrawable);
        /* ~~ COONAX ## END ~~ */

        Intent mySingleView = getIntent();
        mark = (RatingBar) findViewById(R.id.rating_shop);

        mySingleId = mySingleView.getExtras().getInt("id");
        Log.i("PUYDUFOU", "SHOW_SHOP :: Demande d'informations sur la boutique portant l'ID n°" + mySingleId);
        refreshShop();

        mark.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
                                              @Override public void onRatingChanged(RatingBar mark, float rating, boolean fromUser){
                                                  //mark.setRating(rating);
                                                  if(fromUser) {
                                                      mark.setIsIndicator(true);
                                                      Integer userRating = Math.round(rating);
                                                      Toast.makeText(getApplicationContext(), "Merci ! Votre note: " + userRating + "/5", Toast.LENGTH_LONG).show();
                                                      markShop(userRating);
                                                      Log.i("PUYDUFOU", "RATING_SHOP :: Notation de la boutique avec la note " + userRating + "/5 (USER ? " + fromUser + ")");
                                                  }
                                              }
                                          }
        );
    }

    private void markShop(int myUserMark) {
        PuyDuFou puyDuFouService = new RestAdapter.Builder()
                .setEndpoint(PuyDuFou.ENDPOINT)
                .setLog(new AndroidLog("retrofit"))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
                .create(PuyDuFou.class);
        try {
            puyDuFouService.shopMarkAsync(mySingleId, myUserMark, new Callback<Mark>() {
                @Override
                public void success(Mark myMark, Response response) {
                    Log.i("PUYDUFOU", "RATING_SHOP :: La boutique a bien été noté" + myMark.getStatut());
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.w("PUYDUFOU", "RATING_SHOP :: Impossible de noter la boutique");
                    Log.w("PUYDUFOU", "RATING_SHOP :: " + error);
                    Toast.makeText(getApplicationContext(), "FAIL" + error, Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e) {
            Log.w("PUYDUFOU", "RATING_SHOP :: EXCEPTION: " + e);
        }
    }

    private void refreshShop() {
        PuyDuFou puyDuFouService = new RestAdapter.Builder()
                .setEndpoint(PuyDuFou.ENDPOINT)
                .setLog(new AndroidLog("retrofit"))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
                .create(PuyDuFou.class);
        try {
            puyDuFouService.shopAsync(mySingleId, new Callback<Shop>() {
                @Override
                public void success(Shop myShop, Response response) {
                    Log.i("PUYDUFOU", "SHOW_SHOP :: La boutique " + myShop.getName() + " a été réceptionnée avec succès !");
                    Log.i("PUYDUFOU", "SHOW_SHOP :: " + myShop.toString());

                    ImageView image = (ImageView) findViewById(R.id.image_shop);
                    TextView description = (TextView) findViewById(R.id.text_long_desc_shop);
                    RatingBar mark = (RatingBar) findViewById(R.id.rating_shop);
                    TextView latitude = (TextView) findViewById(R.id.text_value_latitude_shop);
                    TextView longitude = (TextView) findViewById(R.id.text_value_longitude_shop);

                    setTitle(WordUtils.capitalize(myShop.getName()));
                    description.setText(myShop.getShortDescription());
                    Picasso.with(getApplicationContext()).load(myShop.getImage()).placeholder(R.drawable.placeholder).fit().centerCrop().into(image);
                    mark.setRating(myShop.getNote().floatValue());
                    latitude.setText(myShop.getLatitude().toString());
                    longitude.setText(myShop.getLongitude().toString());
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.w("PUYDUFOU", "SHOW_SHOP :: Impossible de récupérer la boutique");
                    Log.w("PUYDUFOU", "SHOW_SHOP :: " + error);
                    Toast.makeText(getApplicationContext(), "FAIL" + error, Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e) {
            Log.w("PUYDUFOU", "SHOW_SHOP :: EXCEPTION: " + e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shop, menu);
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
