package concept.omdb.data.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import concept.omdb.data.api.MovieEntry;
import org.greenrobot.greendao.annotation.Generated;

/**
 * DB Entity for {@link MovieEntry}
 */
@SuppressWarnings("WeakerAccess")
@Entity
public class Movie {

    @Id
    private Long id;

    @Index(unique = true)
    private String imdbID;

    private String title;
    private String year;
    private String type;
    private String poster;
    //
    @Generated(hash = 1736116903)
    public Movie(Long id, String imdbID, String title, String year, String type,
            String poster) {
        this.id = id;
        this.imdbID = imdbID;
        this.title = title;
        this.year = year;
        this.type = type;
        this.poster = poster;
    }
    @Generated(hash = 1263461133)
    public Movie() {
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
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getPoster() {
        return this.poster;
    }
    public void setPoster(String poster) {
        this.poster = poster;
    }

}
