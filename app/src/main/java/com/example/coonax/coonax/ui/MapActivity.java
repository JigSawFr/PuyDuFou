package com.example.coonax.coonax.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.coonax.coonax.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends Activity {

    private static final LatLng Park = new LatLng(46.890423,-0.928202);
    GoogleMap googleMap;
    private void createMapView(){
        try{
            if (null == googleMap){
                googleMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.mapView)).getMap();

            }
            if(null == googleMap) {
                Toast.makeText(getApplicationContext(),
                        "Error creating map",Toast.LENGTH_SHORT).show();
            }
        }catch (NullPointerException exception){
            Log.e("mapApp", exception.toString());
        }
    }

    private void setMarker(String markerName,Double longitude,Double latitude){
        if (null!=googleMap){
            googleMap.addMarker(new MarkerOptions().position(new LatLng(longitude, latitude)).title(markerName).draggable(false));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        createMapView();

        Intent mySingleView = getIntent();
        String longitude = null;
        String latitude = null;
        String name = null;
        name = mySingleView.getExtras().getString("name");
        setMarker(name,46.890423,-0.928202);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Park, 15));
        //googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

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
