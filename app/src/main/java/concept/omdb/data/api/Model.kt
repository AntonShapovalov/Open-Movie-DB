package concept.omdb.data.api

import com.google.gson.annotations.SerializedName

/**
 * Response model for [ApiService.getMovies]
 */
data class MoviesResponse(
    @SerializedName("Search")
    val movies: List<MovieEntry>
)

/**
 * Entry of [MoviesResponse.movies]
 */
data class MovieEntry(
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: String,
    val imdbID: String,
    @SerializedName("Type")
    val Type: String,
    @SerializedName("Poster")
    val Poster: String
)