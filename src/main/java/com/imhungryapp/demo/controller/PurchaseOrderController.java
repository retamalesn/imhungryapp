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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.imhungryapp.demo.config.GeneralProperties;
import com.imhungryapp.demo.dto.MapsDto;
import com.imhungryapp.demo.dto.ResponseService;
import com.imhungryapp.demo.dto.SmsDto;
import com.imhungryapp.demo.exception.ResourceNotFoundException;
import com.imhungryapp.demo.model.PurchaseOrder;
import com.imhungryapp.demo.model.Restaurant;
import com.imhungryapp.demo.repository.PurchaseOrderRepository;
import com.imhungryapp.demo.repository.RestaurantRepository;
import com.imhungryapp.demo.service.KafkaProducerService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/purchaseorder")
@Api(value="restaurant", description="Operations pertaining to purchase orders")
public class PurchaseOrderController {

	@Autowired
	PurchaseOrderRepository orderRep;
	@Autowired
	RestaurantRepository restRep;
	@Autowired
	private GeneralProperties app;
	@Autowired
	KafkaProducerService kafkaProducerService;
	Gson gson = new Gson();

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<PurchaseOrder> getOrders() {
		return orderRep.findAll();
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseService createOrder(@Valid @RequestBody PurchaseOrder order) {
		PurchaseOrder orderSaved = orderRep.save(order);
		ResponseService respService = new ResponseService();
		restRep.findById(order.getRestaurantId()).map(restaurant -> {
			restaurant.getPurchaseOrders().add(orderSaved);
			Restaurant restSaved = restRep.save(restaurant);
			calculateTime(restSaved, orderSaved, respService);
			sendEmail();
			sendSms();
			return respService;
		}).orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id " + order.getRestaurantId()));

		return respService;
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteOrder(@PathVariable int id) {
		return orderRep.findById(id).map(order -> {
			orderRep.delete(order);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
	}

	private void calculateTime(Restaurant res, PurchaseOrder order, ResponseService respOrder) {
		MapsDto mapsDTO = new MapsDto();
		mapsDTO.setOrigins(new String[] { res.getLocation() });
		mapsDTO.setDestinations(new String[] { order.getLatLng() });
		kafkaProducerService.sendMessage("maps", gson.toJson(mapsDTO));

		//return distanceMatrixService.getDeliveryTime(origins, destinations);
			
	}

	private void sendEmail() {

		kafkaProducerService.sendMessage("email", gson.toJson(app.getEmail()));
	}
	
	private void sendSms() {
		SmsDto smsDto = new SmsDto();
		smsDto.setNumber("+18444085028746");
		smsDto.setSender("Nico Resto Challenge");
		smsDto.setMsg("We already started to cooking your food!. Delivery Time: ");
		kafkaProducerService.sendMessage("sms", gson.toJson(smsDto));
	}
}
