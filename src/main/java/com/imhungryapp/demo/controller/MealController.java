package com.imhungryapp.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.imhungryapp.demo.model.Meal;
import com.imhungryapp.demo.repository.MealRepository;
import com.imhungryapp.demo.service.KafkaProducerService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/meals")	
@Api(value="restaurant", description="Operations pertaining to meals")
public class MealController {
	
	@Autowired
	MealRepository mr;
	
	@RequestMapping(value="/all", method=RequestMethod.GET)
    public List<Meal> getMeals() {
        return mr.findAll();
    }
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
    public Meal createMeal(@Valid @RequestBody Meal meal) {
        return mr.save(meal);
    }

}
