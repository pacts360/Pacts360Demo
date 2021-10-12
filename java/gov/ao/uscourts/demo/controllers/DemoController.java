package gov.ao.uscourts.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gov.ao.uscourts.demo.services.PactsDemoService;

@RestController
public class DemoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

	@Autowired
	PactsDemoService service;

	@GetMapping(value = "/postRequest")
	public String postRequest(@RequestParam("operation") String operation) {

		return service.makeRequest(operation);

	}
}
