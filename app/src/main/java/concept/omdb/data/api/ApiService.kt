package concept.omdb.data.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Open Movie DB API retrofit service
 */
interface ApiService {

    @GET("/.")
    fun getMovies(
        @Query("apikey") apiKey: String,
        @Query("s") title: String
    ): Observable<MoviesResponse>

    @GET("/.")
    fun getMovieInfo(
        @Query("apikey") apiKey: String,
        @Query("i") imdbID: String
    ): Observable<MovieInfoResponse>

}