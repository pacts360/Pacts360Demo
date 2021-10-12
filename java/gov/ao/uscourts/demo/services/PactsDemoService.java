package gov.ao.uscourts.demo.services;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PactsDemoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PactsDemoService.class);

	private static final String uri = "https://sefdev.jdc.ao.dcn:443/edwpacts/";

	// Inject the authorized client service and authorized client manager
	// from the OAuthClientConfiguration class
	@Autowired
	private AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientServiceAndManager;

	public String makeRequest(String operation) {
		// Build an OAuth2 request for the Jenie provider
		OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId("EDWWebServices")
				.principal("Pacts 360 Demo Service").build();
		// Perform the actual authorization request using the authorized client service
		// and authorized client
		// manager. This is where the access is retrieved from the Jenie server.
		OAuth2AuthorizedClient authorizedClient = this.authorizedClientServiceAndManager.authorize(authorizeRequest);
		// Get the token from the authorized client object
		OAuth2AccessToken accessToken = Objects.requireNonNull(authorizedClient).getAccessToken();

		LOGGER.info("Issued: {}, Expires: {}", accessToken.getIssuedAt().toString(),
				accessToken.getExpiresAt().toString());
		LOGGER.info("Scopes: {}", accessToken.getScopes().toString());
		LOGGER.info("Token: {}", accessToken.getTokenValue());

		////////////////////////////////////////////////////
		// STEP 2: Use the Token and call the service
		////////////////////////////////////////////////////

		// Add the access token to the RestTemplate headers
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken.getTokenValue());
		HttpEntity request = new HttpEntity(headers);

		// Make the actual HTTP GET request
		RestTemplate restTemplate = new RestTemplate();
		String serviceRequest = uri + operation;
		ResponseEntity<String> response = restTemplate.exchange(serviceRequest, HttpMethod.GET, request, String.class);

		String result = response.getBody();
		// response is written to the log file
		LOGGER.info("Reply = \n{}", result);
		return result;
	}

}
