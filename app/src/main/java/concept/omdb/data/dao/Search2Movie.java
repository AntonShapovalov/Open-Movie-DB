package concept.omdb.data.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Many-to-Many Search-to-Movie relation
 */
@SuppressWarnings("WeakerAccess")
@Entity
public class Search2Movie {

    @Id
    private Long id;

    private Long searchId; // FK on Search
    private Long movieId; // FK on Movie
    //
    @Generated(hash = 566655223)
    public Search2Movie(Long id, Long searchId, Long movieId) {
        this.id = id;
        this.searchId = searchId;
        this.movieId = movieId;
    }
    @Generated(hash = 218163733)
    public Search2Movie() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getSearchId() {
        return this.searchId;
    }
    public void setSearchId(Long searchId) {
        this.searchId = searchId;
    }
    public Long getMovieId() {
        return this.movieId;
    }
    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

}
