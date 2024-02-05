package dev.eventcreator.model;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Geolocation {

    private double latitude;
    private double longitude;

    /**
     * Default constructor for Geolocation.
     * Initializes the latitude and longitude to 0.
     */
    public Geolocation() {
        this.latitude = 0;
        this.longitude = 0;
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

    public void setLongitude(double longtitude) {
        this.longitude = longtitude;
    }

    public static Geolocation fromString(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        Geolocation geolocation = null;
        try {
            geolocation = mapper.readValue(jsonString, Geolocation.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return geolocation;
    }

    @Override
    public String toString() {
        String s = "";
        s += "Latitude: " + this.latitude + "\n";
        return s;
    }
}
