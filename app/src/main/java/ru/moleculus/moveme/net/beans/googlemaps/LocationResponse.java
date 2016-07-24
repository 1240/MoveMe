package ru.moleculus.moveme.net.beans.googlemaps;

import ru.moleculus.moveme.BaseConstants;

/**
 * Created by Oleg on 24.03.2016.
 */
public class LocationResponse extends BaseMapsResponse implements BaseConstants {

    private Result[] results;

    private class Result {

        private Geometry geometry;

        private class Geometry {
            private Location location;

            private class Location {
                private double lat = INVALID_LOCATION, lng = INVALID_LOCATION;

                public double getLatitude() {
                    return lat;
                }

                public double getLongitude() {
                    return lng;
                }
            }

            public Location getLocation() {
                return location;
            }
        }

        public Geometry getGeometry() {
            return geometry;
        }

    }

    public double getLatitude() {
        double latitude = INVALID_LOCATION;
        if (results != null && results.length >= 1) {
            Result result = results[0];
            latitude = result.getGeometry().getLocation().getLatitude();
        }
        return latitude;
    }

    public double getLongitude() {
        double longitude = INVALID_LOCATION;
        if (results != null && results.length >= 1) {
            Result result = results[0];
            longitude = result.getGeometry().getLocation().getLongitude();
        }
        return longitude;
    }

}
