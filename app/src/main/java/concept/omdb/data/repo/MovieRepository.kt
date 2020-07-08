package concept.omdb.data.repo

import concept.omdb.data.dao.Movie
import concept.omdb.data.dao.MovieInfo
import concept.omdb.data.dao.Search
import io.reactivex.Observable

/**
 * Repository interface for UI (ViewModels) and testing
 * Dagger app module provides implementation [MovieDataRepository] for UI
 * Testing module provides own fake implementation
 */
interface MovieRepository {

    /**
     * Get movies' list from local cache or from remote API
     * If local cache is expired (see [LocalDataSource.expirationTime]) - reload data from API
     */
    fun getMovies(query: String): Observable<List<Movie>>

    /**
     * Get movie info from local cache or from remote API
     * If local data exists - always return it, never reload from API
     */
    fun getMovieInfo(imdbID: String): Observable<MovieInfo>

    /**
     * Load last search from local DB
     * If search does not exists yet (on first app start), returns empty object (query is null)
     */
    fun getLastSearch(): Observable<Search>

    /**
     * Get all saved queries from local DB
     */
    fun getAllQueries(): Observable<List<String>>

}
