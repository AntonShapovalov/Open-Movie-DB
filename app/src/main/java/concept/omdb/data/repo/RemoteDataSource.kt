package concept.omdb.data.repo

import concept.omdb.data.api.ApiService
import concept.omdb.data.api.MovieEntry
import concept.omdb.data.api.MovieInfoResponse
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Provides Movies data from [ApiService]
 */
@Singleton
class RemoteDataSource @Inject constructor() {

    @Inject lateinit var apiService: ApiService

    private val _apiKey = "74c10a54"

    /**
     * Search movies by title
     */
    fun getMovies(
        apiKey: String = _apiKey,
        title: String
    ): Observable<List<MovieEntry>> = apiService.getMovies(apiKey, title)
        .map { if (it.isSuccessful) it.body()?.movies else throw exception }

    /**
     * Get full movie info by ID
     */
    fun getMovieInfo(
        apiKey: String = _apiKey,
        imdbID: String
    ): Observable<MovieInfoResponse> = apiService.getMovieInfo(apiKey, imdbID)
        .map { if (it.isSuccessful) it.body() else throw exception }

    private val exception: RuntimeException get() = RuntimeException("Unable to get data!")

}