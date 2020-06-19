package concept.omdb.data.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import concept.omdb.data.api.MovieInfoResponse;
import org.greenrobot.greendao.annotation.Generated;

/**
 * DB Entity for {@link MovieInfoResponse}
 */
@SuppressWarnings("WeakerAccess")
@Entity
public class MovieInfo {

    @Id
    private Long id;

    @Index(unique = true)
    private String imdbID;

    private String title;
    private String year;
    private String genre;
    private String director;
    private String actors;
    private String plot;
    private String poster;
    private String metaScore;
    private String imdbRating;
    //
    @Generated(hash = 1297248003)
    public MovieInfo(Long id, String imdbID, String title, String year,
            String genre, String director, String actors, String plot,
            String poster, String metaScore, String imdbRating) {
        this.id = id;
        this.imdbID = imdbID;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.director = director;
        this.actors = actors;
        this.plot = plot;
        this.poster = poster;
        this.metaScore = metaScore;
        this.imdbRating = imdbRating;
    }
    @Generated(hash = 713364488)
    public MovieInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getImdbID() {
        return this.imdbID;
    }
    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getYear() {
        return this.year;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public String getGenre() {
        return this.genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public String getDirector() {
        return this.director;
    }
    public void setDirector(String director) {
        this.director = director;
    }
    public String getActors() {
        return this.actors;
    }
    public void setActors(String actors) {
        this.actors = actors;
    }
    public String getPlot() {
        return this.plot;
    }
    public void setPlot(String plot) {
        this.plot = plot;
    }
    public String getPoster() {
        return this.poster;
    }
    public void setPoster(String poster) {
        this.poster = poster;
    }
    public String getMetaScore() {
        return this.metaScore;
    }
    public void setMetaScore(String metaScore) {
        this.metaScore = metaScore;
    }
    public String getImdbRating() {
        return this.imdbRating;
    }
    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

}
