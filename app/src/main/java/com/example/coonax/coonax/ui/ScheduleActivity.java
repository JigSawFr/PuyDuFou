package com.example.coonax.coonax.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.example.coonax.coonax.R;

public class ScheduleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        //Affichage de la ActionBar coloré
        ActionBar ab = getActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#3498db"));
        ab.setBackgroundDrawable(colorDrawable);

        //Bouton d'accès à l'activité BestScheduleActivity

        Button switchButtonBestSchedule = (Button) findViewById(R.id.button_best_schedule);
        switchButtonBestSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleActivity.this, BestScheduleActivity.class);
                startActivity(intent);
            }
        });

        //Bouton d'accès à l'activité MyScheduleActivity

        Button switchButtonAddSchedule = (Button) findViewById(R.id.button_add_schedule);
        switchButtonAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleActivity.this, MyScheduleActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
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
