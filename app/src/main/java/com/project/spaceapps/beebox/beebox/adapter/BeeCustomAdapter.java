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

import java.util.ArrayList;

/**
 * Created by Matheus on 29/04/2017.
 */

public class BeeCustomAdapter extends ArrayAdapter<Bee> implements View.OnClickListener {

    private ArrayList<Bee> dataSet;
    private Context mContext;

    private static class ViewHolder {
        TextView tv_description_bee;
        LinearLayout ll_linha;
    }

    public BeeCustomAdapter(ArrayList<Bee> data, Context context) {
        super(context, R.layout.row_item_bee, data);

        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        Bee bee = (Bee) object;

    }

    private int lastPosition = -1;

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final Bee bee = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_bee, parent, false);

            viewHolder.tv_description_bee = (TextView) convertView.findViewById(R.id.tv_description_bee);
            viewHolder.ll_linha = (LinearLayout) convertView.findViewById(R.id.ll_linha);

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.tv_description_bee.setText("" + bee.getDescription());

        viewHolder.ll_linha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, InfoBeeActivity.class);
                i.putExtra("id", String.valueOf(bee.getCod()));
                mContext.startActivity(i);
            }
        });

        return convertView;
    }
}
