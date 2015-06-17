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
import com.example.coonax.coonax.adapter.RestaurantsAdapter;
import com.example.coonax.coonax.model.Restaurant;
import com.example.coonax.coonax.service.PuyDuFou;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsActivity extends Activity {

    private SwipeRefreshLayout mySwipeRefreshLayout;
    private List<Restaurant> myRestaurantsList = new ArrayList<>();
    private ListView myRestaurants;
    private RestaurantsAdapter myRestaurantsAdapter;
    private GenericAdapterView genericAdapterView = new GenericAdapterView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);


        /* ~~ COONAX ## START ~~ */
        ActionBar ab = getActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#9b59b6"));
        ab.setBackgroundDrawable(colorDrawable);
        /* ~~ COONAX ## END ~~ */

        final SwipeRefreshLayout mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_restaurants_layout);
        myRestaurants = (ListView) findViewById(R.id.restaurantsList);
        myRestaurantsAdapter = new RestaurantsAdapter(this, myRestaurantsList);
        myRestaurants.setAdapter(myRestaurantsAdapter);
        myRestaurants.setOnItemClickListener(null);

        myRestaurants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mySingleView = new Intent(getApplicationContext(), RestaurantActivity.class);
                mySingleView.putExtra("id", (Integer) view.getTag());
                startActivity(mySingleView);
            }
        });

        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshRestaurantsList();
                mySwipeRefreshLayout.setRefreshing(false);
            }
        });

        mySwipeRefreshLayout.post(new Runnable() {
                                      @Override
                                      public void run() {
                                          mySwipeRefreshLayout.setRefreshing(true);
                                          refreshRestaurantsList();
                                          mySwipeRefreshLayout.setRefreshing(false);
                                      }
                                  }
        );
    }

    private void refreshRestaurantsList() {
        PuyDuFou puyDuFouService = new RestAdapter.Builder()
                .setEndpoint(PuyDuFou.ENDPOINT)
                .setLog(new AndroidLog("retrofit"))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
                .create(PuyDuFou.class);
        try {
            puyDuFouService.listRestaurantAsync(new Callback<List<Restaurant>>() {
                @Override
                public void success(List<Restaurant> restaurants, Response response) {
                    Log.i("PUYDUFOU", "RESTAURANTS_ACTIVITY :: Les " + restaurants.size() + " restaurants ont été réceptionnés avec succès !");
                    Toast.makeText(getApplicationContext(), restaurants.size() + " restaurants disponibles", Toast.LENGTH_LONG).show();
                    myRestaurantsList.clear();
                    myRestaurantsList.addAll(restaurants);
                    myRestaurantsAdapter.notifyDataSetChanged();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.w("PUYDUFOU", "RESTAURANTS_ACTIVITY :: Impossible de récupérer les restaurants");
                    Log.w("PUYDUFOU", "RESTAURANTS_ACTIVITY :: " + error);
                            Toast.makeText(getApplicationContext(), "FAIL" + error, Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e) {
            Log.w("PUYDUFOU", "RESTAURANTS_ACTIVITY :: EXCEPTION: " + e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_restaurants, menu);
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
