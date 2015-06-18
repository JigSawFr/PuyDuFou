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
import android.widget.TextView;
import android.widget.Toast;
import com.example.coonax.coonax.R;
import com.example.coonax.coonax.model.TheMenu;
import com.example.coonax.coonax.service.PuyDuFou;
import org.parceler.apache.commons.lang.WordUtils;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;

public class MenuActivity extends Activity {

    Integer mySingleId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        /* ~~ COONAX ## START ~~ */
        ActionBar ab = getActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#9b59b6"));
        ab.setBackgroundDrawable(colorDrawable);
        /* ~~ COONAX ## END ~~ */

        Intent mySingleView = getIntent();
        mySingleId = mySingleView.getExtras().getInt("id");
        Log.i("PUYDUFOU", "RESTAURANT_MENU_ACTIVITY :: Demande du menu pour le restaurant portant l'ID n°" + mySingleId);
        refreshMenu();
    }

    private void refreshMenu() {
        PuyDuFou puyDuFouService = new RestAdapter.Builder()
                .setEndpoint(PuyDuFou.ENDPOINT)
                .setLog(new AndroidLog("retrofit"))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
                .create(PuyDuFou.class);
        try {
            puyDuFouService.restaurantMenuAsync(mySingleId, new Callback<TheMenu>() {
                @Override
                public void success(TheMenu myTheMenu, Response response) {
                    Log.i("PUYDUFOU", "RESTAURANT_MENU_ACTIVITY :: Le menu " + myTheMenu.getName() + " a été réceptionné avec succès pour le restaurant portant l'ID n°" + mySingleId);
                    Log.i("PUYDUFOU", "RESTAURANT_MENU_ACTIVITY :: " + myTheMenu.toString());

                    TextView price = (TextView) findViewById(R.id.text_price_menu);
                    TextView starter = (TextView) findViewById(R.id.text_value_entree_menu);
                    TextView middle = (TextView) findViewById(R.id.text_value_plat_menu);
                    TextView dessert = (TextView) findViewById(R.id.text_value_dessert_menu);

                    setTitle(WordUtils.capitalize(myTheMenu.getName()));
                    price.setText(Math.round(myTheMenu.getPrice()) + " €");
                    starter.setText(WordUtils.capitalize(myTheMenu.getStarter()));
                    middle.setText(WordUtils.capitalize(myTheMenu.getMiddle()));
                    dessert.setText(WordUtils.capitalize(myTheMenu.getDessert()));
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.w("PUYDUFOU", "RESTAURANT_MENU_ACTIVITY :: Impossible de récupérer le menu pour le restaurant concerné !");
                    Log.w("PUYDUFOU", "RESTAURANT_MENU_ACTIVITY :: " + error);
                    Toast.makeText(getApplicationContext(), "FAIL" + error, Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception e) {
            Log.w("PUYDUFOU", "RESTAURANT_MENU_ACTIVITY :: EXCEPTION: " + e);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
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
