package concept.omdb.data.api

import com.google.gson.annotations.SerializedName

/**
 * Response model for [ApiService.getMovies]
 */
data class MoviesResponse(
    @SerializedName("Search") val movies: List<MovieEntry>?
)

/**
 * Entry of [MoviesResponse.movies]
 */
data class MovieEntry(
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("imdbID") val imdbID: String,
    @SerializedName("Type") val type: String,
    @SerializedName("Poster") val poster: String
)

/**
 * Response model for [ApiService.getMovieInfo]
 */
data class MovieInfoResponse(
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Genre") val genre: String,
    @SerializedName("Director") val director: String,
    @SerializedName("Actors") val actors: String,
    @SerializedName("Plot") val plot: String,
    @SerializedName("Poster") val poster: String,
    @SerializedName("Metascore") val metaScore: String,
    @SerializedName("imdbRating") val imdbRating: String,
    @SerializedName("imdbID") val imdbID: String
)

/**
 * Error message in case of error response
 */
class MovieNotFoundException : RuntimeException("Movie not found!")