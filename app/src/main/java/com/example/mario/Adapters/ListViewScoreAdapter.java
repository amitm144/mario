package com.example.mario.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mario.Model.User;
import com.example.mario.R;

import java.util.ArrayList;

public class ListViewScoreAdapter extends ArrayAdapter<User> {

    private Context mContext;
    private int mResource;



    public ListViewScoreAdapter(@NonNull Context context, int resource, @NonNull ArrayList<User> scores) {
        super(context, resource, scores);
        this.mContext = context;
        this.mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);

        TextView list_item_rank = convertView.findViewById(R.id.list_item_rank);
        TextView list_item_name = convertView.findViewById(R.id.list_item_name);
        TextView list_item_points = convertView.findViewById(R.id.list_item_points);


        User user = getItem(position);

        list_item_name.setText(user.getName());
        list_item_points.setText(String.valueOf(user.getScore()));
        list_item_rank.setText(String.valueOf(position + 1));



        return convertView;
    }


}