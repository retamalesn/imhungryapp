package com.imhungryapp.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imhungryapp.demo.model.Customer;

public interface CustomerRepository  extends JpaRepository<Customer, Long> {

}
