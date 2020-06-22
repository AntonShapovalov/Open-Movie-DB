package concept.omdb.data.repo

import concept.omdb.data.dao.Movie
import concept.omdb.data.dao.MovieInfo
import concept.omdb.data.dao.Search
import io.reactivex.Observable

/**
 * Replace [MovieRepository] for testing of UI
 *
 * Since [LocalDataSource] and [RemoteDataSource] are covered by
 * dedicated tests [LocalDataSourceAndroidTest] and [RemoteDataSourceTest],
 * we just provide fake objects instead of data from real sources
 */
class FakeMovieRepository : MovieRepository() {

    companion object {
        val movieList = listOf(
            Movie(1, "imdb_1", "title_1", "year_1", "type_1", "poster_1"),
            Movie(2, "imdb_2", "title_2", "year_2", "type_2", "poster_2"),
            Movie(3, "imdb_3", "title_3", "year_3", "type_3", "poster_3")
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

        val search = FakeSearch(1, "query_1", System.currentTimeMillis())

        val queries = listOf("query_1")

        class FakeSearch(id: Long, query: String, execDate: Long) : Search(id, query, execDate) {
            override fun getMovies(): MutableList<Movie> {
                return movieList.toMutableList()
            }
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
        return Observable.just(queries)
    }

}

