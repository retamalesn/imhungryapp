package com.imhungryapp.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imhungryapp.demo.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

}
