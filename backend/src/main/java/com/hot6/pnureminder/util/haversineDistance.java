package com.hot6.pnureminder.util;

public class haversineDistance {
    public static double getDistance(double lat1, double lon1, double lat2, double lon2) {

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double distance = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return distance;
    }

}
