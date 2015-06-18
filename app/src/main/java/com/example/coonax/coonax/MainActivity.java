package com.example.coonax.coonax;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.example.coonax.coonax.ui.*;

/**
 * Projet       ~~ PuyDuFou ~~
 ****************************************
 * Créé par JigSaw le 15/06/2015 à 21:37
 ****************************************
 *        ___ ______     ___ _       __
 *       / (_) ____/____/   | |     / /
 *  __  / / / / __/ ___/ /| | | /| / /
 * / /_/ / / /_/ (__  ) ___ | |/ |/ /
 * \____/_/\____/____/_/  |_|__/|__/
 *
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* ~~ COONAX ## START ~~ */
        Button switchButtonActivities = (Button) findViewById(R.id.button_activities);
        switchButtonActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivitiesActivity.class);
                startActivity(intent);
            }
        });

        Button switchButtonProgram = (Button) findViewById(R.id.button_program);
        switchButtonProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SchedulesActivity.class);
                startActivity(intent);
            }
        });

        Button switchButtonPhoto = (Button) findViewById(R.id.button_picture);
        switchButtonPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PictureActivity.class);
                startActivity(intent);
            }
        });

        Button switchButtonNextSchedule = (Button) findViewById(R.id.button_next_activity);
        switchButtonNextSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NextScheduleActivity.class);
                startActivity(intent);
            }
        });

        Button switchButtonFacebook = (Button) findViewById(R.id.button_facebook);
        switchButtonFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NoFinishActivity.class);
                startActivity(intent);
            }
        });

        Button switchButtonTwitter = (Button) findViewById(R.id.button_twitter);
        switchButtonTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NoFinishActivity.class);
                startActivity(intent);
            }
        });

        Button switchButtonGooglePlus = (Button) findViewById(R.id.button_googleplus);
        switchButtonGooglePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NoFinishActivity.class);
                startActivity(intent);
            }
        });




        /* ~~ COONAX ## END ~~ */
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
