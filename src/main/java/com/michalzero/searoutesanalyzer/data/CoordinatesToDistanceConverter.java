package com.michalzero.searoutesanalyzer.data;

import static java.lang.Math.*;

public class CoordinatesToDistanceConverter {

    //Haversine Formula
    public static double getApproximateDistanceBetween(Point a, Point b) {
        final int EARTH_RADIUS = 6371; // Approximate Earth's radius in km

        if (a.equals(b)) {
            return 0D;
        }

        double dLat = (b.getLatitude() - a.getLatitude()) *
                Math.PI / 180.0;
        double dLon = (b.getLongitude() - a.getLongitude()) *
                Math.PI / 180.0;

        double lat1 = a.getLatitude() * Math.PI / 180.0;
        double lat2 = b.getLatitude() * Math.PI / 180.0;

        return 2 * EARTH_RADIUS * asin(sqrt(pow(sin(dLat / 2), 2) + pow(sin(dLon / 2), 2) * cos(lat1) * cos(lat2)));
    }

}
