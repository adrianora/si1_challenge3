package lab3.spring.serpring.model;

public class Serie {

	private String title;
	private String year;
	private String released;
	private String actors;
	private String plot;
	private String awards;
	private String poster;
	private String imdbRating;
	private String imdbID;
	private String myRating = "";
	private String lastEpisodeWatched = "";

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getReleased() {
		return released;
	}

	public void setReleased(String released) {
		this.released = released;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public String getAwards() {
		return awards;
	}

	public void setAwards(String awards) {
		this.awards = awards;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getImdbRating() {
		return imdbRating;
	}

	public void setImdbRating(String imdbRating) {
		this.imdbRating = imdbRating;
	}

	public String getImdbID() {
		return imdbID;
	}

	public void setImdbID(String imdbID) {
		this.imdbID = imdbID;
	}

	public String getMyRating() {
		return myRating;
	}

	public void setMyRating(String myRating) {
		this.myRating = myRating;
	}

	public String getLastEpisodeWatched() {
		return lastEpisodeWatched;
	}

	public void setLastEpisodeWatched(String lastEpisodeWatched) {
		this.lastEpisodeWatched = lastEpisodeWatched;
	}

}
