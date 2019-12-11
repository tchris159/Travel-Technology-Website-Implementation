package com.bharath.location.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bharath.location.model.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {
	
}
