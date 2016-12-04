package edu.wpi.goalify.models.teams;

import edu.wpi.goalify.models.teams.venue.Venue;

/**
 * @author Jules Voltaire on 11/30/2016.
 */

public class Team {
    private String name;
    private int dbid;
    private Venue defaultHomeVenue;
    private boolean isNational;
    private String shortName;

    /**
     *
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return The dbid
     */
    public int getDbid() {
        return dbid;
    }

    /**
     *
     * @param dbid The dbid
     */
    public void setDbid(int dbid) {
        this.dbid = dbid;
    }

    /**
     *
     * @return The defaultHomeVenue
     */
    public Venue getDefaultHomeVenue() {
        return defaultHomeVenue;
    }

    /**
     *
     * @param defaultHomeVenue The defaultHomeVenue
     */
    public void setDefaultHomeVenue(Venue defaultHomeVenue) {
        this.defaultHomeVenue = defaultHomeVenue;
    }

    /**
     *
     * @return The isNational
     */
    public boolean isIsNational() {
        return isNational;
    }

    /**
     *
     * @param isNational The isNational
     */
    public void setIsNational(boolean isNational) {
        this.isNational = isNational;
    }

    /**
     *
     * @return The shortName
     */
    public String getShortName() {
        return shortName;
    }

    /**
     *
     * @param shortName The shortName
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
