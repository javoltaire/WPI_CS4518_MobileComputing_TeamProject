package edu.wpi.goalify.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import edu.wpi.goalify.R;
import edu.wpi.goalify.models.Team;
import edu.wpi.goalify.models.TeamLocation;
import edu.wpi.goalify.sqlite.DBHelper;

/**
 * @author Jules Voltaire on 12/11/2016.
 */

public class TeamsAdapter extends ArrayAdapter<Team> {

    //list of followed teams
    private static ArrayList<Team> followedTeamArrayList;

    private Team team;

    private ToggleButton followTeamBtn;

    private DBHelper dbHelper;

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

        dbHelper = new DBHelper(getContext());
        followedTeamArrayList = new ArrayList<>();

        Cursor cursor = dbHelper.getAllTeams();

        if (cursor.moveToFirst()){
            do{
                int teamID = cursor.getInt(cursor.getColumnIndex("id"));
                String teamName = cursor.getString(cursor.getColumnIndex("team_name"));
                double lat = cursor.getDouble(cursor.getColumnIndex("team_location_lat"));
                double lon = cursor.getDouble(cursor.getColumnIndex("team_location_lon"));

                Team t = new Team(teamID, teamName, new TeamLocation(lat, lon));
                followedTeamArrayList.add(t);
                // do what ever you want here
            }while(cursor.moveToNext());
        }
        cursor.close();

        // Grab the data item for this position, make sure that it is not null
        team = getItem(position);
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

        //toggle button
        followTeamBtn = (ToggleButton) convertView.findViewById(R.id.item_followTeam_toggleButton);

        setupFollowButton();

        followTeamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isFav = false;
                int index = -1;

                //check if team is a favorite
                if (followedTeamArrayList != null){
                    isFav = followedTeamArrayList.contains(team);
                    index = followedTeamArrayList.indexOf(team);
                }

                if (!isFav){  //team is not a favorite

                    //make team a favorite
                    followedTeamArrayList.add(team);

                    followTeamBtn.setChecked(true);

                } else { //team is a favorite

                    //un-favorite team
                    if (index != -1) {
                        followedTeamArrayList.remove(index);
                    }

                    followTeamBtn.setChecked(false);


                }

                //save the array list
                if (followedTeamArrayList != null){
                    for (Team t: followedTeamArrayList){
                        dbHelper.insertTeam(t.getTeamId(), t.getTeamName(), t.getTeamLocation().getTeamLatitude(), t.getTeamLocation().getTeamLongitude());
                    }
                }


            }
        });

        // Return the updated team item view
        return convertView;
    }


    private void setupFollowButton() {

        boolean isFav = false;

        //check if the team is a favorite
        if (followedTeamArrayList != null){
            isFav = followedTeamArrayList.contains(team);
        }

        if (!isFav){  //airlines is not a favorite

            //un-fill favorite image
            followTeamBtn.setChecked(false);
        } else { //team is a favorite

            //fill the favorite image
            followTeamBtn.setChecked(true);

        }
    }
    //endregion
}
