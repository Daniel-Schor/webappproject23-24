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

    /**
     * Returns the latitude coordinate of the location.
     *
     * @return the latitude coordinate
     */
    public double getLatitude() {
        return this.latitude;
    }

    /**
     * Returns the longitude coordinate of the location.
     *
     * @return the longitude coordinate
     */
    public double getLongitude() {
        return this.longitude;
    }

    /**
     * Sets the latitude coordinate of the location.
     *
     * @param latitude the latitude coordinate to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Sets the longitude coordinate of the location.
     *
     * @param longitude the longitude coordinate to set
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
