package com.project.spaceapps.beebox.beebox.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.spaceapps.beebox.beebox.InfoBeeActivity;
import com.project.spaceapps.beebox.beebox.R;
import com.project.spaceapps.beebox.beebox.model.Bee;
import com.project.spaceapps.beebox.beebox.model.Place;

import java.util.ArrayList;

/**
 * Created by Matheus on 30/04/2017.
 */

public class PlaceCustomAdapter extends ArrayAdapter<Place> implements View.OnClickListener {

    private ArrayList<Place> dataSet;
    private Context mContext;

    private static class ViewHolder {
        TextView tv_description_place;
        TextView tv_city_place;
        LinearLayout ll_line;
    }

    public PlaceCustomAdapter(ArrayList<Place> data, Context context) {
        super(context, R.layout.row_item_place, data);

        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        Place place = (Place) object;

    }

    private int lastPosition = -1;

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final Place place = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_place, parent, false);

            viewHolder.tv_description_place = (TextView) convertView.findViewById(R.id.tv_description_place);
            viewHolder.tv_city_place = (TextView) convertView.findViewById(R.id.tv_city_place);

            viewHolder.ll_line = (LinearLayout) convertView.findViewById(R.id.ll_line);

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.tv_description_place.setText("" + place.getDescription());
        viewHolder.tv_city_place.setText("" + place.getCity());

        return convertView;
    }
}