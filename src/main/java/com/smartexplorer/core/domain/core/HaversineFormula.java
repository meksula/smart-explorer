package com.smartexplorer.core.domain.core;

import com.google.maps.model.LatLng;

/**
 * @Author
 * Karol Meksuła
 * 21-06-2018
 * */

public class HaversineFormula implements Haversine {
    private static final int EARTH_RADIUS_KM = 6371;

    @Override
    public double distance(LatLng pointA, LatLng pointB) {
        double latitudePointA = pointA.lat;
        double longitudePointA = pointA.lng;
        double latidutePointB = pointB.lat;
        double longitudePointB = pointB.lng;

        double latitudeDelta = Math.toRadians((latidutePointB - latitudePointA));
        double longitudeDelta = Math.toRadians((longitudePointB - longitudePointA));

        latitudePointA = Math.toRadians(latitudePointA);
        latidutePointB = Math.toRadians(latidutePointB);

        double a = haversinFormula(latitudeDelta) + Math.cos(latitudePointA) * Math.cos(latidutePointB) * haversinFormula(longitudeDelta);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }

    private double haversinFormula(double value) {
        return Math.pow(Math.sin(value / 2), 2);
    }

}
