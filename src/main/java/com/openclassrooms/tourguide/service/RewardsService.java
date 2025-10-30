package com.openclassrooms.tourguide.service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import rewardCentral.RewardCentral;
import com.openclassrooms.tourguide.model.User;
import com.openclassrooms.tourguide.model.UserReward;

@Service
public class RewardsService {
	private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;

	// proximity in miles
	private final int defaultProximityBuffer = 10;
	private int proximityBuffer = defaultProximityBuffer;
	private int attractionProximityRange = 200;
	private final GpsUtil gpsUtil;
	private final RewardCentral rewardsCentral;

	public RewardsService(GpsUtil gpsUtil, RewardCentral rewardCentral) {
		this.gpsUtil = gpsUtil;
		this.rewardsCentral = rewardCentral;
	}

	public void setProximityBuffer(int proximityBuffer) {
		this.proximityBuffer = proximityBuffer;
	}

	public void setDefaultProximityBuffer() {
		proximityBuffer = defaultProximityBuffer;
	}

	public void calculateRewards(User user) {
			List<VisitedLocation> userLocations = user.getVisitedLocations();
			List<Attraction> attractions = gpsUtil.getAttractions();
			CopyOnWriteArrayList<VisitedLocation> locations = new CopyOnWriteArrayList<>(userLocations);
			CopyOnWriteArrayList<Attraction> attractions1 = new CopyOnWriteArrayList<>(attractions);
				for (VisitedLocation location : locations){

					for (Attraction attraction : attractions1) {
						if (user.getUserRewards().stream().noneMatch(r -> r.attraction.attractionName.equals(attraction.attractionName))) {
							if (nearAttraction(location, attraction)) {
								user.addUserReward(new UserReward(location, attraction, getRewardPoints(attraction, user)));
							}
						}
					}

				}
	}

	public void calculateRewards(List<User> users) {
		List<CompletableFuture<Boolean>> futures = new ArrayList<>();
		ExecutorService executorService = Executors.newFixedThreadPool(5000);
		try {
			for (User user : users) {
				CompletableFuture<Boolean> completableFuture = CompletableFuture.supplyAsync(() -> {
					try {
						calculateRewards(user);
					} catch (Exception e) {
						System.out.println("Error : " + e.getMessage());
					}
					return true;
				}, executorService);
				futures.add(completableFuture);
			}
			CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
			System.out.println("Nombre d'utilisateur dont le calcul des récompenses ont était traiter : " + users.size());
		} finally {
			executorService.shutdown();
		}
	}
	
	public boolean isWithinAttractionProximity(Attraction attraction, Location location) {
		return !(getDistance(attraction, location) > attractionProximityRange);
	}
	
	private boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction) {
		return !(getDistance(visitedLocation.location, attraction) > proximityBuffer);
	}
	
	public int getRewardPoints(Attraction attraction, User user) {
		return rewardsCentral.getAttractionRewardPoints(attraction.attractionId, user.getUserId());
	}
	
	public double getDistance(Location loc1, Location loc2) {
        double lat1 = Math.toRadians(loc1.latitude);
        double lon1 = Math.toRadians(loc1.longitude);
        double lat2 = Math.toRadians(loc2.latitude);
        double lon2 = Math.toRadians(loc2.longitude);

        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                               + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        double nauticalMiles = 60 * Math.toDegrees(angle);
        return STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
	}

}
