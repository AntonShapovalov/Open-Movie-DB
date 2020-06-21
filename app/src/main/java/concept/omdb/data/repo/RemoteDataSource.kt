package concept.omdb.data.repo

import concept.omdb.data.api.*
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
     * Response can be either [MoviesResponse] or ErrorResponse with message,
     * in case of error message just always show "Movie Not Found!"
     */
    fun getMovies(
        apiKey: String = _apiKey,
        title: String
    ): Observable<List<MovieEntry>> = apiService.getMovies(apiKey, title)
        .map { it.movies ?: throw MovieNotFoundException() }

    /**
     * Get full movie info by ID
     * Only [MovieInfoResponse] is expected, because imdbID is based on previous response
     */
    fun getMovieInfo(
        apiKey: String = _apiKey,
        imdbID: String
    ): Observable<MovieInfoResponse> = apiService.getMovieInfo(apiKey, imdbID)

}