package lab3.spring.serpring.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lab3.spring.serpring.model.Profile;
import lab3.spring.serpring.model.Serie;

@RestController
public class DashboardController implements IDashboardController {
	
	private ProfileController profile;
	
	@Autowired
	public DashboardController() {
		this.profile = new ProfileController();
	}
	
	@Override
	public ResponseEntity<Collection<Profile>> usersListResponse() {
		Collection<Profile> allUsersResponse = (Collection<Profile>) this.profile.all();
		HttpStatus status = HttpStatus.NOT_FOUND;
		if (!allUsersResponse.isEmpty())
			status = HttpStatus.OK;		
		return new ResponseEntity<Collection<Profile>>(allUsersResponse, status);
	}
	
	@Override
	public ResponseEntity<Profile> userSearchResponse(@RequestBody Profile user) {
		Profile searchUserRequest = this.profile.search(user);
		HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
		if (searchUserRequest != null)
			status = HttpStatus.OK;		
		return new ResponseEntity<Profile>(searchUserRequest, status);
	}
	
	@Override
	public ResponseEntity<Profile> subscribeUser(@RequestBody Profile user) {
		Profile newUser = this.profile.add(user);
		HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
		if (newUser != null)
			status = HttpStatus.OK;
		return new ResponseEntity<Profile>(newUser, status);
	}

	@Override
	public ResponseEntity<Profile> isLogged() {
		Profile userSession = this.profile.getSession();
		HttpStatus status = HttpStatus.NOT_FOUND;
		if (userSession != null)
			status = HttpStatus.OK;
		return new ResponseEntity<Profile>(userSession, status);
	}

	@Override
	public ResponseEntity<Profile> login(@RequestBody Profile user) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		if (this.profile.isLogged())
			status = HttpStatus.CONFLICT;
		else if (!this.profile.contains(user))
			status = HttpStatus.NOT_ACCEPTABLE;
		else if (this.profile.login(user))
			status = HttpStatus.OK;
		return new ResponseEntity<Profile>(this.profile.getSession(), status);
	}

	@Override
	public ResponseEntity<Profile> logout(@RequestBody Profile user) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		if (this.profile.logout(user))
			status = HttpStatus.OK;
		return new ResponseEntity<Profile>(this.profile.getSession(), status);
	}
	
	@Override
	public ResponseEntity<Collection<Serie>> seriesResponse() {
		Collection<Serie> seriesResponse = null;
		HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
		if (this.profile.isLogged()) {
			seriesResponse = this.profile.getSession().watchingResponse();
			status = HttpStatus.OK;
		}
		return new ResponseEntity<Collection<Serie>>(seriesResponse, status);
	}

	@Override
	public ResponseEntity<Serie> addSerieToProfile(@RequestBody Serie serie) {
		HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
		Serie newSerie = null;
		if (this.profile.isLogged()) {
			newSerie = this.profile.getSession().addToWatch(serie);
			if (newSerie != null)
				status = HttpStatus.OK;
		}		
		return new ResponseEntity<Serie>(newSerie, status);
	}
	
	@Override
	public ResponseEntity<Serie> updateSerieFromProfile(@RequestBody Serie serie) {
		HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
		Serie newSerie = null;
		if (this.profile.isLogged()) {
			newSerie = this.profile.getSession().updateToWatch(serie);
			if (newSerie != null)
				status = HttpStatus.OK;
		}		
		return new ResponseEntity<Serie>(newSerie, status);
	}

	@Override
	public ResponseEntity<Serie> removeSerieFromProfile(@PathVariable String idSerie) {
		HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
		Serie serieDeleted = null;
		if (this.profile.isLogged()) {
			serieDeleted = this.profile.getSession().removeFromWatching(idSerie);
			if (serieDeleted != null)
				status = HttpStatus.OK;
		}		
		return new ResponseEntity<Serie>(serieDeleted, status);
	}

	@Override
	public ResponseEntity<Collection<Serie>> watchlistResponse() {
		Collection<Serie> seriesResponse = null;
		HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
		if (this.profile.isLogged()) {
			seriesResponse = this.profile.getSession().watchlistResponse();
			status = HttpStatus.OK;
		}
		return new ResponseEntity<Collection<Serie>>(seriesResponse, status);
	}

	@Override
	public ResponseEntity<Serie> addSerieToWatchlist(@RequestBody Serie serie) {
		HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
		Serie newSerie = null;
		if (this.profile.isLogged()) {
			newSerie = this.profile.getSession().addToWatchlist(serie);
			if (newSerie != null)
				status = HttpStatus.OK;
		}		
		return new ResponseEntity<Serie>(newSerie, status);
	}

	@Override
	public ResponseEntity<Serie> removeSerieFromWatchlist(@PathVariable String idSerie) {
		HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
		Serie serieDeleted = null;
		if (this.profile.isLogged()) {
			serieDeleted = this.profile.getSession().removeFromWatchlist(idSerie);
			if (serieDeleted != null)
				status = HttpStatus.OK;
		}		
		return new ResponseEntity<Serie>(serieDeleted, status);
	}

}
