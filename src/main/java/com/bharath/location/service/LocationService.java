package com.bharath.location.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bharath.location.model.Location;

@Service
public interface LocationService {

	// Take a location model object and save it into the database or pass it into
	// the Data Access Layer and returns a location
	Location saveLocation(Location location);

	Location updateLocation(Location location); // updates and returns that updated location back

	void deleteLocation(Location location);

	Optional<Location> getLocationById(int id); // passes in Id

	List<Location> getAlllocation();

}
