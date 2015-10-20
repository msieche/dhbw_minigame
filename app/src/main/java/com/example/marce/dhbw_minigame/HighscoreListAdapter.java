package com.example.marce.dhbw_minigame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by marce on 08.09.2015.
 */
public class HighscoreListAdapter extends ArrayAdapter<Highscore> {
    public HighscoreListAdapter(Context context, ArrayList<Highscore> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Highscore highscore = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.highscore_list, parent, false);
        }
        // Lookup view for data population
        TextView playerName = (TextView) convertView.findViewById(R.id.highscore_playerName);
        TextView level = (TextView) convertView.findViewById(R.id.highscore_level);
        TextView points = (TextView) convertView.findViewById(R.id.highscore_points);
        // Populate the data into the template view using the data object

        playerName.setText(highscore.getPlayername());
        level.setText(String.valueOf(highscore.getLevel()));
        points.setText(String.valueOf(highscore.getPoints()));
        // Return the completed view to render on screen
        return convertView;
    }
}