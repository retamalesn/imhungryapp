package com.imhungryapp.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TrafficModel;
import com.google.maps.model.TravelMode;
import com.imhungryapp.demo.config.GeneralProperties;
import com.imhungryapp.demo.dto.DeliveryTime;
import com.imhungryapp.demo.dto.DistanceMatrixItem;
import com.imhungryapp.demo.dto.DistanceMatrixResponse;
import com.imhungryapp.demo.dto.ResponseOrder;
import com.imhungryapp.demo.exception.ResourceNotFoundException;
import com.imhungryapp.demo.model.Customer;
import com.imhungryapp.demo.model.PurchaseOrder;
import com.imhungryapp.demo.model.Restaurant;
import com.imhungryapp.demo.repository.CustomerRepository;
import com.imhungryapp.demo.repository.PurchaseOrderRepository;
import com.imhungryapp.demo.repository.RestaurantRepository;
import com.imhungryapp.demo.service.DistanceMatrixService;
import com.imhungryapp.demo.service.EmailService;
import com.imhungryapp.demo.service.SmsService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/purchaseorder")
@Api(value="restaurant", description="Operations pertaining to purchase orders")
public class PurchaseOrderController {
	
	private static final Logger LOGGER = LogManager.getLogger(PurchaseOrderController.class);

	@Autowired
	PurchaseOrderRepository orderRep;
	@Autowired
	RestaurantRepository restRep;
	@Autowired
	CustomerRepository custRep;
	@Autowired
	private DistanceMatrixService distanceMatrixService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private SmsService smsService;
	@Autowired
	private GeneralProperties app;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<PurchaseOrder> getOrders() {
		return orderRep.findAll();
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseOrder createOrder(@Valid @RequestBody PurchaseOrder order) {
		PurchaseOrder orderSaved = orderRep.save(order);
		ResponseOrder respOrder = new ResponseOrder(new DeliveryTime());
		Restaurant restSaved = null;
		Customer custSaved = null;
		Optional<Restaurant> restOptional = restRep.findById(order.getRestaurantId());
		if (restOptional.isPresent()){
			restOptional.get().getPurchaseOrders().add(orderSaved);
			restSaved = new Restaurant();
			restSaved = restRep.save(restOptional.get());
		}
		else{
			LOGGER.warn("Restaurant could not find");
		}
		Optional<Customer> custOptional = custRep.findById(order.getCustomerId());
		if (custOptional.isPresent()){
			custOptional.get().getOrders().add(orderSaved);
			custSaved  = new Customer();
			custSaved = custRep.save(custOptional.get());
		}
		else{
			LOGGER.warn("Customer could not find");
		}

		if(restSaved != null && custSaved != null) {
			calculateTime(restSaved, orderSaved, respOrder);
			sendEmail(respOrder, custSaved);
			sendSms(respOrder, custSaved);
		}
		
		return respOrder;
	}
	
	@RequestMapping(value="/update", method=RequestMethod.PUT)
    public PurchaseOrder updateOrder(@Valid @RequestBody PurchaseOrder order) {
        return orderRep.save(order);
    }

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteOrder(@PathVariable int id) {
		return orderRep.findById(id).map(order -> {
			orderRep.delete(order);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
	}

	private ResponseOrder calculateTime(Restaurant res, PurchaseOrder order, ResponseOrder respOrder) {
		String[] origins = new String[] { res.getLocation() };
		String[] destinations = new String[] { order.getLatLng() };
		try {
			DistanceMatrix distanceMatrix = distanceMatrixService.getDitanceMatrix(origins, destinations,
					TravelMode.DRIVING, "en", "", "", new DateTime().plus(Duration.standardMinutes(2)),
					TrafficModel.PESSIMISTIC);
			DistanceMatrixResponse response = new DistanceMatrixResponse(distanceMatrix);
			for (DistanceMatrixItem di : response.getDistanceMatrixItems()) {
				respOrder.getDeliveryTime().setTime(di.getDistanceMatrixElement().getDurationInTraffic().getHumanReadable());
			}
			respOrder.getDeliveryTime().setStatus("Delivery time calculated");
		} catch (IOException e) {
			respOrder.getDeliveryTime().setStatus("Wrong API KEY");
			respOrder.getDeliveryTime().setTime("Time couldn't calculated!");
		} catch (Exception e) {
			respOrder.getDeliveryTime().setStatus("Wrong API KEY");
			respOrder.getDeliveryTime().setTime("Time couldn't calculated!");
		}
		return respOrder;
	}

	private ResponseOrder sendEmail(ResponseOrder respOrder, Customer customer) {

		try {
			app.getEmail().setContent(app.getEmail().getContent()+respOrder.getDeliveryTime().getTime());
			emailService.sendSimpleMessage(app.getEmail(), customer.getEmail());
			respOrder.setEmailStatus("Email sent!");
		}catch(Exception ex) {
			respOrder.setEmailStatus("Verify email information!");
		}
		return respOrder;
	}
	
	private ResponseOrder sendSms(ResponseOrder respOrder, Customer customer) {

		respOrder.setSmsStatus(smsService.sendSms(customer.getPhoneNumber(), "Nico Resto Challenge", "We already started to cooking your food!. Delivery Time: "+respOrder.getDeliveryTime()));
		return respOrder;
	}
}
