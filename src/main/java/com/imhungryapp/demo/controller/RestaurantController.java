package com.imhungryapp.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.imhungryapp.demo.exception.ResourceNotFoundException;
import com.imhungryapp.demo.exception.RestaurantException;
import com.imhungryapp.demo.model.Restaurant;
import com.imhungryapp.demo.model.Review;
import com.imhungryapp.demo.repository.RestaurantRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/restaurants")
@Api(value="restaurant", description="Operations pertaining to restaurants")
public class RestaurantController {

	@Autowired
	RestaurantRepository rr;

	@ApiOperation(value = "View a list of available restaurants", response = List.class)
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Restaurant> getRestaurants() {
		List<Restaurant> list = rr.findAll();
		return list;
	}

	@ApiOperation(value = "View a list of available restaurants filtered by rating", response = List.class)
	@RequestMapping(value = "/byrating/{rating}", method = RequestMethod.GET)
	public List<Restaurant> getRestaurantsByRating(@PathVariable float rating) throws RestaurantException{
		return rr.findByRating(rating);
	}

	@ApiOperation(value = "Add a Restaurant", response = Restaurant.class)
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Restaurant createRestaurant(@Valid @RequestBody Restaurant restaurant) {
		if(restaurant.getRating() == null) {
			restaurant.setRating(new Float(0.0));
		}
		Restaurant restSaved = rr.save(restaurant);
		calulateRating(restSaved);
		return rr.save(restSaved);
	}
	
	@ApiOperation(value = "Delete a Restaurant", response = ResponseEntity.class)
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteRestaurant(@PathVariable int id) {
		return rr.findById(id).map(restaurant -> {
			rr.delete(restaurant);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id " + id));
	}
	
	

	private void calulateRating(Restaurant restaurant) {
		Float reviewSum = 0F;
		for (Review review : restaurant.getReviews()) {
			reviewSum += review.getRating() != null ? review.getRating() : 0F;
		}
		restaurant.setRating(reviewSum / restaurant.getReviews().size());
	}

}
