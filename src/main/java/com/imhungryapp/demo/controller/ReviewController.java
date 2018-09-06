package com.imhungryapp.demo.controller;

import java.util.List;
import java.util.Optional;

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
import com.imhungryapp.demo.model.Restaurant;
import com.imhungryapp.demo.model.Review;
import com.imhungryapp.demo.repository.RestaurantRepository;
import com.imhungryapp.demo.repository.ReviewRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/review")
@Api(value="restaurant", description="Operations pertaining to reviews")
public class ReviewController {
	
	@Autowired
	ReviewRepository rr;
	@Autowired
	RestaurantRepository restRepo;
	
	@RequestMapping(value="/all", method=RequestMethod.GET)
    public List<Review> getReviews() {
        return rr.findAll();
    }
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
    public Review createReview(@Valid @RequestBody Review review) {
		Review savedReview = null;
		Optional<Restaurant> restOptional = restRepo.findById(review.getRestaurantId());
		if(restOptional.isPresent()) {
			savedReview = rr.save(review);
			restOptional.get().getReviews().add(savedReview);
			this.calulateRating(restOptional.get());
			restRepo.save(restOptional.get());
		}
        return savedReview;
    }
	
	@RequestMapping(value="/update", method=RequestMethod.PUT)
    public Review updateReview(@Valid @RequestBody Review review) {
        return rr.save(review);
    }
	
	@ApiOperation(value = "Delete a Review", response = ResponseEntity.class)
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteReview(@PathVariable int id) {
		return rr.findById(id).map(review -> {
			rr.delete(review);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Review not found with id " + id));
	}
	
	private void calulateRating(Restaurant restaurant) {
		Float reviewSum = 0F;
		for (Review review : restaurant.getReviews()) {
			reviewSum += review.getRating() != null ? review.getRating() : 0F;
		}
		restaurant.setRating(reviewSum / restaurant.getReviews().size());
	}

}
