package edu.wpi.goalify.models.teams.venue;

/**
 * @author Jules Voltaire on 11/30/2016.
 */

public class Venue {
    private int dbid;
    private Geolocation geolocation;
    private int capacity;
    private String name;

    /**
     *@return The dbid
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
     * @return The geolocation
     */
    public Geolocation getGeolocation() {
        return geolocation;
    }

    /**
     *
     * @param geolocation The geolocation
     */
    public void setGeolocation(Geolocation geolocation) {
        this.geolocation = geolocation;
    }

    /**
     *
     * @return The capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     *
     * @param capacity The capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

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
}
