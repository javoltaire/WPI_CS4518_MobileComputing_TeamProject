package edu.wpi.goalify.dataservice;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * @author Jules Voltaire on 12/12/2016.
 */

public class FirebaseUtil {
    //region Constants
    public static final String TEAMS = "teams";
    //endregion

    //region Private Variables
    private static DatabaseReference sDatabaseReference = FirebaseDatabase.getInstance().getReference();
    //endregion

    //region Database references
    public static DatabaseReference getBaseReference(){
        return sDatabaseReference;
    }

    public static DatabaseReference getTeamsReference(){
        return sDatabaseReference.child(TEAMS);
    }
    //endregion
}
