package com.smartexplorer.core.domain.geolocation;

/**
 * @Author
 * Karol Meksuła
 * 15-06-2018
 * */

public class BasicAddress {
    private String city;
    private String street;
    private String buildigNumber;

    public BasicAddress(String city, String street, String buildigNumber) {
        this.city = city;
        this.street = street;
        this.buildigNumber = buildigNumber;
    }

    @Override
    public String toString() {
        return city + ", " + street + " " + buildigNumber;
    }
}
