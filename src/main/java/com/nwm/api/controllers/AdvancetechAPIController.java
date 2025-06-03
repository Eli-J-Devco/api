/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;

import javax.validation.Valid;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.AdvanceTechControlEntity;
import com.nwm.api.entities.SitesDevicesEntity;
import com.nwm.api.services.SitesDevicesService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/advance-tech-api")
public class AdvancetechAPIController extends BaseController {

	
	/**
	 * @description call API advanceTech
	 * @author long.pham
	 * @since 2021-11-05
	 * @param obj
	 */

	@PostMapping("/save-control")
	public Object saveControl(@Valid @RequestBody AdvanceTechControlEntity obj) {
		try {
			SitesDevicesService service = new SitesDevicesService();
			SitesDevicesEntity params = new SitesDevicesEntity();
			params.setHash_id(obj.getHash_id());
			params.setId_employee(obj.getId_employee());
			SitesDevicesEntity getDetail = service.getDetail(params);

//			if(getDetail.getId() > 0 && getDetail.getAdvance_tech_host() != null && getDetail.getAdvance_tech_pass() != null) {
			// Login
			String url = getDetail.getAdvance_tech_host() + "/" + obj.getSerial_number() + "/sys/log_in?Timeout=0";
			String json = "{\"password\":\"" + obj.getAdvance_tech_pass() + "\"}";
			// Create HttpClient
			try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

				// Create HttpPut request
				HttpPut request = new HttpPut(url);
				// Set JSON payload
				StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
				request.setEntity(entity);

				// Set headers
				request.setHeader("Content-Type", "application/json");
				request.setHeader("Referer", "https://route.nextwavemonitoring.com");

				// Execute the request
				try (CloseableHttpResponse response = httpClient.execute(request)) {

					// Get HttpResponse Status
//		                System.out.println("Response Code: " + response.getStatusLine().getStatusCode());
					if (response.getStatusLine().getStatusCode() == 200) {
						String content = EntityUtils.toString(response.getEntity());
						JSONParser parser = new JSONParser();
						JSONObject jsonSession = (JSONObject) parser.parse(content);
						String session_id = jsonSession != null ? jsonSession.get("session_id").toString() : null;

						if (session_id != null) {
							String tagKey = ""; String tagValue = ""; String message = "";
							if (obj.getDevice_type().equals("inv") && obj.getStatus_type().equals("restart")) {
								tagKey = obj.getAdvance_tech_field_restart();
								tagValue = obj.getAdvance_tech_value_restart();
								message = "Restart successful";
							}
							if (obj.getDevice_type().equals("inv") && obj.getStatus_type().equals("stop")) {
								tagKey = obj.getAdvance_tech_field_stop();
								tagValue = obj.getAdvance_tech_value_stop();
								message = "Stop successful";
							}
							if (obj.getDevice_type().equals("inv") && obj.getStatus_type().equals("start")) {
								tagKey = obj.getAdvance_tech_field_start();
								tagValue = obj.getAdvance_tech_value_start();
								message = "Start successful";
							}
							
							String urlControl = getDetail.getAdvance_tech_host() + "/" + obj.getSerial_number()  +"/data/tags/" + tagKey + "/value";
							String jsonControl = "{\"value\":\"" + tagValue + "\"}";
							
							try (CloseableHttpClient httpClientControl = HttpClients.createDefault()) {
								// Create HttpPut request
								HttpPut requestControl = new HttpPut(urlControl);
								// Set JSON payload
								StringEntity entityControl = new StringEntity(jsonControl, ContentType.APPLICATION_JSON);
								requestControl.setEntity(entityControl);
								// Set headers
								requestControl.setHeader("Content-Type", "application/json");
								requestControl.setHeader("Referer", "https://route.nextwavemonitoring.com");
								requestControl.setHeader("Cookie", ("SID=" + session_id));
								

								// Execute the request
								try (CloseableHttpResponse responseControl = httpClientControl.execute(requestControl)) {

									// Get HttpResponse Status
//									System.out.println( "Response Code: " + responseControl.getStatusLine().getStatusCode());
									if (responseControl.getStatusLine().getStatusCode() == 200) {
										return this.jsonResult(true, message, obj, 1);
									}

									// Get HttpResponse Content
//									String contentControl = EntityUtils.toString(responseControl.getEntity());
//									System.out.println("Response Content: \n" + contentControl);
								} catch (Exception e) {
									return this.jsonResult(false, "Cannot connect to server", obj, 1);
								}
								
								
							} catch (Exception e) {
								return this.jsonResult(false, "Incorrect Password", obj, 1);
							}
						} else {
							return this.jsonResult(false, "Incorrect Password", obj, 1);
						}

					} else {
						return this.jsonResult(false, "Incorrect Password", obj, 1);
					}
				}
				return this.jsonResult(true, null, obj, 1);
			} catch (Exception e) {
				return this.jsonResult(false, "Cannot connect to server", obj, 1);
			}

			
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}
}
