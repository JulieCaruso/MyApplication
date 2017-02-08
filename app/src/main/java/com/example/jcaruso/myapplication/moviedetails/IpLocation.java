package com.example.jcaruso.myapplication.moviedetails;

import com.google.gson.annotations.Expose;

public class IpLocation {
    @Expose
    private String city;

    public IpLocation() {

    }

    public String getCity() {
        return city;
    }
}
