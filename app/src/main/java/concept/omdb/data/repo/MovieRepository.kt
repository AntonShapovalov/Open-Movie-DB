package concept.omdb.data.repo

import concept.omdb.data.dao.Movie
import concept.omdb.data.dao.MovieInfo
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Provides movies' data from local db or remote source
 */
@Singleton
class MovieRepository @Inject constructor() {

    @Inject lateinit var localData: LocalDataSource
    @Inject lateinit var remoteData: RemoteDataSource

    /**
     * Get movies' list from local cache or from remote API
     * If local cache is expired (see [LocalDataSource.expirationTime]) - reload data from API
     */
    fun getMovies(query: String): Observable<List<Movie>> =
        localMovies(query).switchIfEmpty(Observable.defer { remoteMovies(query) })

    /**
     * Get movie info from local cache or from remote API
     * If local data exists - always return it, never reload from API
     */
    fun getMovieInfo(imdbID: String): Observable<MovieInfo> =
        localMovieInfo(imdbID).switchIfEmpty(Observable.defer { remoteMovieInfo(imdbID) })

    /**
     * Private functions to keep public functions' code shorter and readable
     */
    private fun localMovies(query: String): Observable<List<Movie>> = Observable
        .fromCallable { localData.getMovies(query) }
        .filter { it.isNotEmpty() }

    private fun remoteMovies(query: String): Observable<List<Movie>> = remoteData
        .getMovies(title = query)
        .doOnNext { localData.saveMovies(query, it) }
        .map { localData.getMovies(query) }

    private fun localMovieInfo(imdbID: String): Observable<MovieInfo> = Observable
        .fromCallable { localData.getMovieInfo(imdbID) }
        .filter { it.imdbID.isNotEmpty() }

    private fun remoteMovieInfo(imdbID: String): Observable<MovieInfo> = remoteData
        .getMovieInfo(imdbID = imdbID)
        .doOnNext { localData.saveMovieInfo(it) }
        .map { localData.getMovieInfo(imdbID) }

}