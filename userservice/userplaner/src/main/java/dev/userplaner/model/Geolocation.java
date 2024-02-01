package dev.userplaner.model;

/**
 * Represents a geographical location with latitude and longitude coordinates.
 */
public class Geolocation {

    private double latitude;
    private double longitude;

    /**
     * Constructs a new Geolocation object with default latitude and longitude values.
     */
    public Geolocation() {
    }

    /**
     * Constructs a new Geolocation object with the specified latitude and longitude.
     *
     * @param latitude  the latitude coordinate of the location
     * @param longitude the longitude coordinate of the location
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
