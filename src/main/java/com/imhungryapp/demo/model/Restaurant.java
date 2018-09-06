package com.imhungryapp.demo.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import io.swagger.annotations.ApiModelProperty;


@Entity
public class Restaurant  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@ApiModelProperty( value = "The standard id attribute - System generated", required = false)
	private int id;
	private String logo;
	@ApiModelProperty(required = true)
	private String legalName;
	@ApiModelProperty(notes = "The app generated rating")
	private Float rating;
	@ApiModelProperty(required = true)
	private String comercialEmail;
	@ApiModelProperty(required = true)
	private String adminNumber;
	@ApiModelProperty(required = true)
	private String address;
	@ApiModelProperty(required = true)
	private String location;
    @ManyToMany(fetch=FetchType.EAGER)
    @ApiModelProperty(required = false, hidden=true)
    @JoinTable(name = "restaurant_meals", joinColumns = { @JoinColumn(name = "rest_id") }, inverseJoinColumns = {
			@JoinColumn(name = "meal_id") })
    private Set<Meal> meals = new HashSet<>();
	@OneToMany (fetch=FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "rest_id")
	@ApiModelProperty(required = false, hidden=true)
	private Set<Review> reviews = new HashSet<>();
	@OneToMany (fetch=FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "rest_id")
	@ApiModelProperty(required = false, hidden=true)
	private Set<PurchaseOrder> purchaseOrders = new HashSet<>();
	
	public Restaurant() {

    }

    public Restaurant(String logo, String legalName, Float rating, String comercialEmail, String adminNumber,
    		String address, String location) {
        this.logo = logo;
        this.legalName = legalName;
        this.rating = 0F;
        this.comercialEmail = comercialEmail;
        this.adminNumber = adminNumber;
        this.address = address;
        this.location = location;
    }
    
    public Restaurant(String logo, String legalName, Float rating, String comercialEmail, String adminNumber,
    		String address, String location, Set<Meal> meals) {
        this.logo = logo;
        this.legalName = legalName;
        this.rating = rating;
        this.comercialEmail = comercialEmail;
        this.adminNumber = adminNumber;
        this.address = address;
        this.location = location;
        this.meals = meals;
    }	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public String getComercialEmail() {
		return comercialEmail;
	}

	public void setComercialEmail(String comercialEmail) {
		this.comercialEmail = comercialEmail;
	}

	public String getAdminNumber() {
		return adminNumber;
	}

	public void setAdminNumber(String adminNumber) {
		this.adminNumber = adminNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Set<Meal> getMeals() {
		return meals;
	}

	public void setMeals(Set<Meal> meals) {
		this.meals = meals;
	}

	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	public Set<PurchaseOrder> getPurchaseOrders() {
		return purchaseOrders;
	}

	public void setPurchaseOrders(Set<PurchaseOrder> purchaseOrders) {
		this.purchaseOrders = purchaseOrders;
	}

	public void addMeal(Meal meal) {
		meals.add(meal);
		meal.getRestaurants().add(this);
	}

	public void removeMeal(Meal meal) {
		meals.remove(meal);
		meal.getRestaurants().remove(this);
	}
	
	

}
