package dev.eventplaner.model;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Geolocation{

    private double latitude;
    private double longitude;

<<<<<<< HEAD
=======
    /**
     * Constructs a new Geolocation object.
     */
>>>>>>> c2f6757743f990f85505a8f097a152722f5d9046
    public Geolocation() {
        this.latitude = 0;
        this.longitude = 0;
    }

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
