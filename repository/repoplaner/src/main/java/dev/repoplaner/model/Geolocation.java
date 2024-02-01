package dev.repoplaner.model;

import java.io.Serializable;

/**
 * The Geolocation class represents a geographical location specified by
 * latitude and longitude coordinates.
 */
public class Geolocation implements Serializable{

    private static final long serialVersionUID = 5L;
    
    private double latitude;
    private double longitude;

    /**
     * Constructs a new Geolocation object.
     */
    public Geolocation() {
    }
    
    /**
     * Constructs a Geolocation with the specified latitude and longitude.
     *
     * @param latitude  The latitude of the geolocation.
     * @param longitude The longitude of the geolocation.
     */
    public Geolocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
