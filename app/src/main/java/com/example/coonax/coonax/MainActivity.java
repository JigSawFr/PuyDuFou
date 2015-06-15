package com.example.coonax.coonax;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.coonax.coonax.model.Shop;
import com.example.coonax.coonax.service.*;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;
import android.view.View;
import android.widget.Button;

import com.example.coonax.coonax.ui.ActivitiesActivity;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toast.makeText(getApplicationContext(), "TEST", Toast.LENGTH_SHORT).show();
        PuyDuFou puyDuFouService = new RestAdapter.Builder()
                .setEndpoint(PuyDuFou.ENDPOINT)
                .setLog(new AndroidLog("retrofit"))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
                .create(PuyDuFou.class);

       puyDuFouService.listShopAsync(new Callback<List<Shop>>() {
            @Override
            public void success(List<Shop> shops, Response response) {
                Log.d("RESTFULL", "NICE ON A LES BOUTIQUES !!!!");
                afficherShops(shops);
            }

           @Override
            public void failure(RetrofitError error) {
               Toast.makeText(getApplicationContext(), "FAIL" + error, Toast.LENGTH_SHORT).show();
               Log.w("RESTFULL", "FAIL: " + error);
            }
        });
    }

    public void afficherShops(List<Shop> shops) {
        //Toast.makeText(this, "Nb boutiques : " + shops.size(), Toast.LENGTH_SHORT).show();
        Log.d("RESTFULL", "On a " + shops.size() + " boutiques chez PuyDuOUF !");

        Button switchButtonActivities = (Button) findViewById(R.id.button_activities);


        switchButtonActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivitiesActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
