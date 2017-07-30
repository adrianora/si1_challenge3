package lab3.spring.serpring.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Profile extends User {

	private Map<String, Serie> watching;
	private Map<String, Serie> watchlist;

	public Profile() {
		this.watching = new HashMap<String, Serie>();
		this.watchlist = new HashMap<String, Serie>();
	}

	public Collection<Serie> watchingResponse() {
		return this.watching.values();
	}

	public Serie addToWatch(Serie serie) {
		if (serie != null) {
			if (!this.watching.containsKey(serie.getImdbID()))
				this.watching.put(serie.getImdbID(), serie);
			if (this.watchlist.containsKey(serie.getImdbID()))
				this.watchlist.remove(serie.getImdbID());
		}
		return serie;
	}
	
	public Serie updateToWatch(Serie serie) {
		return this.watching.put(serie.getImdbID(), serie);
	}

	public Serie removeFromWatching(String idSerie) {
		return this.watching.remove(idSerie);
	}

	public Collection<Serie> watchlistResponse() {
		return this.watchlist.values();
	}

	public Serie addToWatchlist(Serie serie) {
		if (serie != null) {
			if (this.watching.containsKey(serie.getImdbID()))
				serie = null;
			else if (!this.watchlist.containsKey(serie.getImdbID()))
				this.watchlist.put(serie.getImdbID(), serie);
		}
		return serie;
	}

	public Serie removeFromWatchlist(String idSerie) {
		return this.watchlist.remove(idSerie);
	}

}
