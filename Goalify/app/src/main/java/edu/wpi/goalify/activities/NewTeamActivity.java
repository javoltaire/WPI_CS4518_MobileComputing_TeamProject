package edu.wpi.goalify.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.goalify.R;
import edu.wpi.goalify.simplemodels.Team;

public class NewTeamActivity extends AppCompatActivity {
    //region private final strings
    private final String TEAMS_URL = "https://goalify-9d5b4.firebaseio.com/teams";
    //endregion

    //region Private variables
    DatabaseReference teamsReference = FirebaseDatabase.getInstance().getReference().child("teams");
    //endregion

    //region Private View Instances
    private EditText mTeamNameEditText;
    private LinearLayout mLocalsAndLeaguesLinearLayout;
    private LinearLayout mTeamsSearchResultLinearLayout;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_team);
        setupActionBar();
        initControls();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home :
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //region Event Handlers

    /**
     * Grabs the text from {@link #mTeamNameEditText} then performs a search
     * @param view the calling view
     */
    public void buttonSearchTeamsOnClick(View view) {
        String teamName = mTeamNameEditText.getText().toString();
        searchTeams(teamName);
    }
    //endregion

    //region Private Methods
    private void initControls(){
        mTeamNameEditText = (EditText) findViewById(R.id.editText_search_team_name);
        mLocalsAndLeaguesLinearLayout = (LinearLayout) findViewById(R.id.linearLayout_locals_and_leagues);
        mTeamsSearchResultLinearLayout = (LinearLayout) findViewById(R.id.linearLayout_teams_search_results);
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(R.string.add_new_team);
    }

    /**
     * Submits a request to firebase to get a list of teams
     * @param teamName The name of the team to search for.
     */
    private void searchTeams(String teamName){
        DatabaseReference teamsReference = FirebaseDatabase.getInstance().getReference().child("teams");
        Query queryReference = teamsReference.orderByChild("teamName").startAt(teamName).endAt("b\uf8ff");
        final List<Object> objs = new ArrayList<>();
        queryReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Team team = dataSnapshot.getValue(Team.class);
                Log.e("Team", team.getTeamName());
                objs.add(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mLocalsAndLeaguesLinearLayout.setVisibility(View.GONE);
        mTeamsSearchResultLinearLayout.setVisibility(View.VISIBLE);
    }
    //endregion
}
