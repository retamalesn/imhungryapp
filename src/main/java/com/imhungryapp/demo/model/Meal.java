package com.imhungryapp.demo.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "MEAL")
public class Meal  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String description;
	private Float price;
	@Column(name="restaurant_id")
	private int restaurantId;
	
	public Meal() {

    }

    public Meal(String name, String description, Float price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
    
    public Meal(String name, String description, Float price, Set<Restaurant> rest) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.setRestaurants(rest);
    }
	
    @ManyToMany(mappedBy = "meals")
    @JsonIgnore
    private Set<Restaurant> restaurants = new HashSet<>();
	
	@ManyToMany(mappedBy = "meals")
	@JsonIgnore
    private Set<PurchaseOrder> purchaseOrders = new HashSet<>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Set<Restaurant> getRestaurants() {
		return restaurants;
	}
	public void setRestaurants(Set<Restaurant> restaurants) {
		this.restaurants = restaurants;
	}
	public Set<PurchaseOrder> getPurchaseOrders() {
		return purchaseOrders;
	}
	public void setPurchaseOrders(Set<PurchaseOrder> purchaseOrders) {
		this.purchaseOrders = purchaseOrders;
	}

	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
