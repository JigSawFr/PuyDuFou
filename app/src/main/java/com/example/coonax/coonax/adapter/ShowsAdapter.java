package com.example.coonax.coonax.adapter;

import com.example.coonax.coonax.R;
import com.example.coonax.coonax.app.AppController;
import com.example.coonax.coonax.model.Show;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

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

public class ShowsAdapter extends BaseAdapter {


    private Activity activity;
    private LayoutInflater inflater;
    private List<Show> showsList;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public ShowsAdapter(Activity activity, List<Show> showsList) {
        this.activity = activity;
        this.showsList = showsList;
    }

    @Override
    public int getCount() {
        return showsList.size();
    }

    @Override
    public Object getItem(int location) {
        return showsList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null) { inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE); }
        if (convertView == null) { convertView = inflater.inflate(R.layout.list_row, null); }
        if (imageLoader == null) { imageLoader = AppController.getInstance().getImageLoader(); }

        NetworkImageView image = (NetworkImageView) convertView.findViewById(R.id.image);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView description = (TextView) convertView.findViewById(R.id.description);
        TextView time = (TextView) convertView.findViewById(R.id.time);

        Show myShow = showsList.get(position);
        image.setImageUrl(myShow.getImage(), imageLoader);
        title.setText(myShow.getName());
        description.setText(myShow.getShortDesciption());
        convertView.setTag(new Integer(Integer.valueOf(myShow.getId())));
        time.setText(myShow.getLenght());

        return convertView;
    }

}
