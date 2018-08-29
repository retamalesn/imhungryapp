package com.imhungryapp.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.imhungryapp.demo.model.Review;
import com.imhungryapp.demo.repository.ReviewRepository;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/review")
@Api(value="restaurant", description="Operations pertaining to reviews")
public class ReviewController {
	
	@Autowired
	ReviewRepository rr;
	
	@RequestMapping(value="/all", method=RequestMethod.GET)
    public List<Review> getReviews() {
        return rr.findAll();
    }
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
    public Review createReview(@Valid @RequestBody Review review) {
        return rr.save(review);
    }

}
