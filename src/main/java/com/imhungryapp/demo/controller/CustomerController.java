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
import com.imhungryapp.demo.model.Customer;
import com.imhungryapp.demo.repository.CustomerRepository;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/customers")	
public class CustomerController {

	@Autowired
	CustomerRepository cr;
	
	@RequestMapping(value="/all", method=RequestMethod.GET)
    public List<Customer> getCustomer() {
        return cr.findAll();
    }

	@RequestMapping(value="/add", method=RequestMethod.POST)
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        return cr.save(customer);
    }
	
	@RequestMapping(value="/update", method=RequestMethod.PUT)
    public Customer updateCustomer(@Valid @RequestBody Customer customer) {
        return cr.save(customer);
    }
	
	@ApiOperation(value = "Delete a Customer", response = ResponseEntity.class)
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable long id) {
		return cr.findById(id).map(customer -> {
			cr.delete(customer);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
	}
}
