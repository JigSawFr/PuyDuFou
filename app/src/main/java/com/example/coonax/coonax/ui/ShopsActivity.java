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
import com.example.coonax.coonax.adapter.ShopsAdapter;
import com.example.coonax.coonax.model.Shop;
import com.example.coonax.coonax.service.PuyDuFou;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.List;

public class ShopsActivity extends Activity {

    private SwipeRefreshLayout mySwipeRefreshLayout;
    private List<Shop> myShopsList = new ArrayList<>();
    private ListView myShops;
    private ShopsAdapter myShopsAdapter;
    private GenericAdapterView genericAdapterView = new GenericAdapterView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);

        /* ~~ COONAX ## START ~~ */
        ActionBar ab = getActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#3498db"));
        ab.setBackgroundDrawable(colorDrawable);
        /* ~~ COONAX ## END ~~ */

        final SwipeRefreshLayout mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_shops_layout);
        myShops = (ListView) findViewById(R.id.shopsList);
        myShopsAdapter = new ShopsAdapter(this, myShopsList);
        myShops.setAdapter(myShopsAdapter);
        myShops.setOnItemClickListener(null);

        myShops.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mySingleView = new Intent(getApplicationContext(), ShopActivity.class);
                mySingleView.putExtra("id", (Integer) view.getTag());
                startActivity(mySingleView);
            }
        });

        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshShopsList();
                mySwipeRefreshLayout.setRefreshing(false);
            }
        });

        mySwipeRefreshLayout.post(new Runnable() {
                                      @Override
                                      public void run() {
                                          mySwipeRefreshLayout.setRefreshing(true);
                                          refreshShopsList();
                                          mySwipeRefreshLayout.setRefreshing(false);
                                      }
                                  }
        );
    }

    private void refreshShopsList() {
        PuyDuFou puyDuFouService = new RestAdapter.Builder()
                .setEndpoint(PuyDuFou.ENDPOINT)
                .setLog(new AndroidLog("retrofit"))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
                .create(PuyDuFou.class);
        try {
            puyDuFouService.listShopAsync(new Callback<List<Shop>>() {
                @Override
                public void success(List<Shop> shops, Response response) {
                    Log.i("PUYDUFOU", "SHOPS_ACTIVITY :: Les " + shops.size() + " boutiques ont été réceptionnés avec succès !");
                    Toast.makeText(getApplicationContext(), shops.size() + " boutiques disponibles", Toast.LENGTH_LONG).show();
                    myShopsList.clear();
                    myShopsList.addAll(shops);
                    myShopsAdapter.notifyDataSetChanged();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.w("PUYDUFOU", "SHOPS_ACTIVITY :: Impossible de récupérer les boutiques");
                    Log.w("PUYDUFOU", "SHOPS_ACTIVITY :: " + error);
                    Toast.makeText(getApplicationContext(), "FAIL" + error, Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e) {
            Log.w("PUYDUFOU", "SHOPS_ACTIVITY :: EXCEPTION: " + e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shops, menu);
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
