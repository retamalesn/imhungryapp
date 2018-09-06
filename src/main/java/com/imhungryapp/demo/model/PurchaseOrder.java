package com.imhungryapp.demo.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class PurchaseOrder implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@ApiModelProperty( value = "The standard id attribute - System generated", required = false)
	private int id;
	private Float totalCost;
	private String address;
	private String latLng;
	@Column(name="rest_id")
	private int restaurantId;
	@Column(name="customer_id")
	private long customerId;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "orderpurchase_meals", joinColumns = { @JoinColumn(name = "orderpurchase_id") }, inverseJoinColumns = {
			@JoinColumn(name = "meal_id") })
	private Set<Meal> meals = new HashSet<>();
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Float getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(Float totalCost) {
		this.totalCost = totalCost;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLatLng() {
		return latLng;
	}
	public void setLatLng(String latLng) {
		this.latLng = latLng;
	}
	public Set<Meal> getMeals() {
		return meals;
	}
	public void setMeals(Set<Meal> meals) {
		this.meals = meals;
	}	
	public void addMeal(Meal meal) {
		meals.add(meal);
		meal.getPurchaseOrders().add(this);
	}

	public void removeMeal(Meal meal) {
		meals.remove(meal);
		meal.getPurchaseOrders().remove(this);
	}
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
