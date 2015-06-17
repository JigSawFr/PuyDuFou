package com.example.coonax.coonax.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.coonax.coonax.R;
import com.example.coonax.coonax.model.Shop;
import org.parceler.apache.commons.lang.WordUtils;

import java.util.List;

/**
 * Projet       ~~ PuyDuFou ~~
 ****************************************
 * Créé par JigSaw le 17/06/2015 à 03:45
 ****************************************
 *        ___ ______     ___ _       __
 *       / (_) ____/____/   | |     / /
 *  __  / / / / __/ ___/ /| | | /| / /
 * / /_/ / / /_/ (__  ) ___ | |/ |/ /
 * \____/_/\____/____/_/  |_|__/|__/
 *
 */

public class ShopsAdapter extends BaseAdapter {


    private Activity activity;
    private LayoutInflater inflater;
    private List<Shop> shopsList;

    public ShopsAdapter(Activity activity, List<Shop> shopsList) {
        this.activity = activity;
        this.shopsList = shopsList;
    }

    @Override
    public int getCount() {
        return shopsList.size();
    }

    @Override
    public Object getItem(int location) {
        return shopsList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null) { inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE); }
        if (convertView == null) { convertView = inflater.inflate(R.layout.list_row_shops, null); }

        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView description = (TextView) convertView.findViewById(R.id.description);

        Shop myShop = shopsList.get(position);
        title.setText(WordUtils.capitalize(myShop.getName()));
        description.setText(myShop.getShortDescription());
        convertView.setTag(new Integer(Integer.valueOf(myShop.getId())));

        return convertView;
    }

}
