package edu.wpi.goalify.models.venues;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Jules Voltaire on 11/30/2016.
 * This class represents a geolocation with a longitude and a latitude
 */
public class Geolocation {
    //region Private variables
    @SerializedName("latitude")
    @Expose
    private double latitude;

    @SerializedName("longitude")
    @Expose
    private double longitude;
    //endregion

    //region Getters and Setters

    //region Latitude
    /**
     * @return The latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude The latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    //endregion

    //region Longitude
    /**
     * @return The longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude The longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    //endregion

    //endregion





}
