package com.imhungryapp.demo.service;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.ReadableInstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.maps.DirectionsApi.RouteRestriction;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TrafficModel;
import com.google.maps.model.TravelMode;
import com.google.maps.model.Unit;
import com.imhungryapp.demo.config.GeneralProperties;
import com.imhungryapp.demo.dto.ResponseService;
import com.imhungryapp.demo.model.DistanceMatrixItem;
import com.imhungryapp.demo.model.DistanceMatrixResponse;
import com.imhungryapp.demo.util.Constants;

/**
 * This is the client for invoking Google DistanceMatrix API
 */
@Component
public class DistanceMatrixService {

	@Autowired
	private GeneralProperties app;

	public DistanceMatrixService() {
	}

	public ResponseService getDeliveryTime(String[] origins, String[] destinations) {
		DistanceMatrix distanceMatrix;
		ResponseService rs = new ResponseService();
		try {
			distanceMatrix = this.getDitanceMatrix(origins, destinations, TravelMode.DRIVING, "en", "", "",
					new DateTime().plus(Duration.standardMinutes(2)), TrafficModel.PESSIMISTIC);
			DistanceMatrixResponse response = new DistanceMatrixResponse(distanceMatrix);
			for (DistanceMatrixItem di : response.getDistanceMatrixItems()) {
				rs.setMessage(di.getDistanceMatrixElement().getDurationInTraffic().getHumanReadable());
				rs.setStatus(Constants.SUCCESS);
			}
		} catch (Exception e) {
			rs.setMessage(e.getMessage());
			rs.setStatus(Constants.FAIL);
		}
		return rs;
	}

	/**
	 * avoid=tolls, highways, ferries, indoor
	 * 
	 * units=metric (default) , imperial
	 * 
	 * @throws Exception
	 */
	public DistanceMatrix getDitanceMatrix(String[] origins, String[] destinations, TravelMode travelMode,
			String language, String unit, String avoid, ReadableInstant departureTime, TrafficModel trafficModel)
			throws Exception {

		final String API_KEY = app.getGoogleApiKey();

		// set up key
		GeoApiContext distCalcer = new GeoApiContext.Builder().apiKey(API_KEY).build();

		DistanceMatrixApiRequest request = DistanceMatrixApi.newRequest(distCalcer);
		request = request.origins(origins).destinations(destinations);

		if (travelMode != null) {
			request = request.mode(travelMode);
		}

		if (StringUtils.isNotBlank(language)) {
			request = request.language(language);
		}

		if (StringUtils.isNotBlank(unit)) {
			Unit unitEnum = Unit.valueOf(unit.toUpperCase());
			if (unitEnum != null) {
				request = request.units(unitEnum);
			}
		}

		if (StringUtils.isNotBlank(avoid)) {
			RouteRestriction routeRestriction = RouteRestriction.valueOf(avoid.trim().toUpperCase());
			if (routeRestriction != null) {
				request = request.avoid(routeRestriction);
			}
		}

		if (departureTime != null) {
			request = request.departureTime(departureTime);
		}

		if (trafficModel != null) {
			request = request.trafficModel(trafficModel);
		}

		DistanceMatrix matrix = request.await();
		return matrix;
	}

}
