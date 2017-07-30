package lab3.spring.serpring.controller;

import java.util.Collection;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lab3.spring.serpring.model.Profile;
import lab3.spring.serpring.model.Serie;

public interface IDashboardController {

	@RequestMapping(value = "/login", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Profile> isLogged();

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Profile> login(Profile user);

	@RequestMapping(value = "/logout", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Profile> logout(Profile user);
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Profile>> usersListResponse();

	@RequestMapping(value = "/profile", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Profile> subscribeUser(Profile user);
	
	@RequestMapping(value = "/profile/search", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Profile> userSearchResponse(Profile user);

	@RequestMapping(value = "/profile/series", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Serie>> seriesResponse();

	@RequestMapping(value = "/profile/series", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Serie> addSerieToProfile(Serie serie);
	
	@RequestMapping(value = "/profile/series", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Serie> updateSerieFromProfile(Serie serie);

	@RequestMapping(value = "/profile/series/{idSerie}", method = RequestMethod.DELETE)
	public ResponseEntity<Serie> removeSerieFromProfile(String idSerie);

	@RequestMapping(value = "/profile/watchlist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Serie>> watchlistResponse();

	@RequestMapping(value = "/profile/watchlist", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Serie> addSerieToWatchlist(Serie serie);

	@RequestMapping(value = "/profile/watchlist/{idSerie}", method = RequestMethod.DELETE)
	public ResponseEntity<Serie> removeSerieFromWatchlist(String idSerie);

}
