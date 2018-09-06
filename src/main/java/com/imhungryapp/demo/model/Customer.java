package com.imhungryapp.demo.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Customer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@ApiModelProperty( value = "The standard id attribute - System generated", required = false)
	private Long id;
	@ApiModelProperty(required = true)
	private String name;
	@ApiModelProperty(required = false)
	private String lastName;
	@ApiModelProperty(required = true)
	private String email;
	@ApiModelProperty(required = true)
	private String phoneNumber;
	@ApiModelProperty(required = true)
	private String address;
	@ApiModelProperty(required = true)
	private String latLng;
	
	@OneToMany (fetch=FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "customer_id")
	@ApiModelProperty(required = false, hidden=true)
	private Set<PurchaseOrder> orders = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Set<PurchaseOrder> getOrders() {
		return orders;
	}

	public void setOrders(Set<PurchaseOrder> orders) {
		this.orders = orders;
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
	
	
}
