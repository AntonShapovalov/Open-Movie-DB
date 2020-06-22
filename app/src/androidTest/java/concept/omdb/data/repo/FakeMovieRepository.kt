package concept.omdb.data.repo

import concept.omdb.data.dao.Movie
import concept.omdb.data.dao.MovieInfo
import concept.omdb.data.dao.Search
import io.reactivex.Observable
import java.util.*

/**
 * Replace [MovieRepository] for testing of UI
 *
 * Since [LocalDataSource] and [RemoteDataSource] are covered by
 * dedicated tests [LocalDataSourceAndroidTest] and [RemoteDataSourceTest],
 * we just provide fake objects instead of data from real sources
 */
class FakeMovieRepository : MovieRepository() {

    val movieList = arrayListOf(
        Movie(1, "imdb_1", "title_1", "year_1", "type_1", "poster_1")
    )

    val movieInfo = MovieInfo(
        1,
        "imdb_1",
        "title_1",
        "year_1",
        "genre_1",
        "director_1",
        "actors_1",
        "plot_1",
        "poster_1",
        "metaScore_1",
        "imdbRating_1"
    )

    val search = FakeSearch(1, "query_1", System.currentTimeMillis(), movieList)

    class FakeSearch(
        id: Long,
        query: String,
        execDate: Long,
        private var movieList: ArrayList<Movie>
    ) : Search(id, query, execDate) {
        override fun getMovies(): MutableList<Movie> {
            return movieList
        }
    }

    override fun getMovies(query: String): Observable<List<Movie>> {
        return Observable.just(movieList)
    }

    override fun getMovieInfo(imdbID: String): Observable<MovieInfo> {
        return Observable.just(movieInfo)
    }

    override fun getLastSearch(): Observable<Search> {
        return Observable.just(search)
    }

    override fun getAllQueries(): Observable<List<String>> {
        return Observable.just(listOf("query_1"))
    }

}

