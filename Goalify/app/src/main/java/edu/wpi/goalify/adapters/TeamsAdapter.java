package edu.wpi.goalify.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.wpi.goalify.R;
import edu.wpi.goalify.models.Team;

/**
 * @author Jules Voltaire on 12/11/2016.
 */

public class TeamsAdapter extends ArrayAdapter<Team> {
    //region Constructor/s
    /**
     * Initializes a new instance of this class
     * @param context
     * @param teams
     */
    public TeamsAdapter(Context context, ArrayList<Team> teams){
        super(context, R.layout.item_team, teams);
    }
    //endregion

    //region Overridden Methods

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Grab the data item for this position, make sure that it is not null
        Team team = getItem(position);
        if(team == null)
            return super.getView(position, convertView, parent);

        // Check if an existing view is being reused, otherwise inflate the view
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_team, parent, false);
        }

        // Grabbing the widgets that need to be updated with data
        TextView tvTeamName = (TextView)convertView.findViewById(R.id.item_teamName_textVIew);

        // Setting the new data
        tvTeamName.setText(team.getTeamName());

        // Return the updated team item view
        return convertView;
    }
    //endregion
}
