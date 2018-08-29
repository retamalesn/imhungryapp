package com.imhungryapp.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imhungryapp.demo.model.Meal;

public interface MealRepository extends JpaRepository<Meal, Integer> {

}
