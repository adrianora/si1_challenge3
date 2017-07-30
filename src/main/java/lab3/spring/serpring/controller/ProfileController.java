package lab3.spring.serpring.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lab3.spring.serpring.model.Profile;

@Service
public class ProfileController {

	private Profile userSession;
	private Map<String, Profile> users;

	@Autowired
	public ProfileController() {
		this.users = new HashMap<String, Profile>();
		this.userSession = null;
	}

	public boolean isLogged() {
		return this.userSession != null;
	}

	public Profile getSession() {
		return this.userSession;
	}

	public Collection<Profile> all() {
		return this.users.values();
	}
	
	public boolean contains(Profile profile) {
		return this.users.containsKey(profile.getEmail());
	}
	
	public Profile add(Profile profile) {
		if (!thisProfileIsNull(profile) && !users.containsKey(profile.getEmail()))
			users.put(profile.getEmail(), profile);
		else
			profile = null;
		return profile;
	}

	public Profile remove(String id) {
		Profile userRemoved = this.users.get(id);
		if (userRemoved != null)
			this.users.remove(userRemoved);
		return userRemoved;
	}
	
	public Profile search(Profile profile) {
		return this.users.get(profile.getEmail());
	}
	
	public boolean login(Profile user) {
		if (!isLogged() && this.users.containsKey(user.getEmail()))
			this.userSession = this.users.get(user.getEmail());
		return this.userSession != null;
	}

	public boolean logout(Profile user) {
		boolean logged = false;
		if (user != null && isLogged()) {
			this.userSession = null;
			logged = true;
		}
		return !isLogged() && logged;
	}
	
	private boolean thisProfileIsNull(Profile profile) {
		return profile.getName() == null || profile.getEmail() == null || profile.getPass() == null;
	}

}
