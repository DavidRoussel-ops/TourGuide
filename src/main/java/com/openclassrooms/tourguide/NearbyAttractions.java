package com.openclassrooms.tourguide;

import com.openclassrooms.tourguide.user.UserReward;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;

public class NearbyAttractions {

    private String name;

    private Attraction attraction;

    private VisitedLocation visitedLocation;

    private double distance;

    private UserReward userReward;

    public NearbyAttractions() {
        super();
    }

    public NearbyAttractions(String name, Attraction attraction, VisitedLocation visitedLocation, double distance, UserReward userReward) {
        this.name = name;
        this.attraction = attraction;
        this.visitedLocation = visitedLocation;
        this.distance = distance;
        this.userReward = userReward;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Attraction getAttraction() {
        return attraction;
    }

    public void setAttraction(Attraction attraction) {
        this.attraction = attraction;
    }

    public VisitedLocation getVisitedLocation() {
        return visitedLocation;
    }

    public void setVisitedLocation(VisitedLocation visitedLocation) {
        this.visitedLocation = visitedLocation;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public UserReward getUserReward() {
        return userReward;
    }

    public void setUserReward(UserReward userReward) {
        this.userReward = userReward;
    }
}
