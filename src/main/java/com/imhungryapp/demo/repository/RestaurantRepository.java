package com.imhungryapp.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.imhungryapp.demo.model.Restaurant;

@Repository
public interface RestaurantRepository  extends JpaRepository<Restaurant, Integer> {
	
	@Query("SELECT r FROM Restaurant r WHERE r.rating = ?1")
    List<Restaurant> findByRating(Float rating);

}
