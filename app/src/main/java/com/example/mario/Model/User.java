package com.example.mario.Model;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private int score ;
    private double lat , lon;


    public User(String name, int score, double lat, double lon) {
        this.name = name;
        this.score = score;
        this.lat = lat;
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public int getScore() {
        return score;
    }

    public User setScore(int score) {
        this.score = score;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public User setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public User setLon(double lon) {
        this.lon = lon;
        return this;
    }
}
