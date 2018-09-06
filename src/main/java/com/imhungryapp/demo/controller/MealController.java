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
import com.imhungryapp.demo.model.Meal;
import com.imhungryapp.demo.model.Restaurant;
import com.imhungryapp.demo.repository.MealRepository;
import com.imhungryapp.demo.repository.RestaurantRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/meals")	
@Api(value="restaurant", description="Operations pertaining to meals")
public class MealController {
	
	@Autowired
	MealRepository mr;
	@Autowired
	RestaurantRepository rr;
	
	@RequestMapping(value="/all", method=RequestMethod.GET)
    public List<Meal> getMeals() {
        return mr.findAll();
    }
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
    public Meal createMeal(@Valid @RequestBody Meal meal) {
		Meal savedMeal = null;
		Optional<Restaurant> restOptional = rr.findById(meal.getRestaurantId());
		if(restOptional.isPresent()) {
			savedMeal = mr.save(meal);
			restOptional.get().addMeal(savedMeal);
			rr.save(restOptional.get());
		}
        return savedMeal;
    }
	
	@RequestMapping(value="/update", method=RequestMethod.PUT)
    public Meal updateMeal(@Valid @RequestBody Meal meal) {
        return mr.save(meal);
    }
	
	@ApiOperation(value = "Delete a Meal", response = ResponseEntity.class)
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteMeal(@PathVariable int id) {
		return mr.findById(id).map(meal -> {
			mr.delete(meal);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Meal not found with id " + id));
	}

}
