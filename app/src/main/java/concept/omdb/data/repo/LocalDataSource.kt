package concept.omdb.data.repo

import concept.omdb.data.api.MovieEntry
import concept.omdb.data.api.MovieInfoResponse
import concept.omdb.data.dao.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.abs

/**
 * Provide Movies' data from local DB
 */
@Singleton
class LocalDataSource @Inject constructor(private val daoSession: DaoSession) {

    private val movieDao: MovieDao = daoSession.movieDao
    private val movieInfoDao: MovieInfoDao = daoSession.movieInfoDao
    private val searchDao: SearchDao = daoSession.searchDao
    private val search2MovieDao: Search2MovieDao = daoSession.search2MovieDao

    // keeps every search result one month without update from remote API
    internal val expirationTime = TimeUnit.DAYS.toMillis(30)

    /**
     * Save search query from UI and movies from API response
     */
    fun saveMovies(query: String, movies: List<MovieEntry>) {
        if (movies.isEmpty()) return
        daoSession.runInTx {
            val dbSearch = saveSearch(query)
            val moviesIds = saveMovies(movies)
            updateSearch2Movies(dbSearch, moviesIds)
        }
    }

    /**
     * Get local saved movies
     * If local cache is expired (older than [expirationTime]), return empty list
     */
    fun getMovies(query: String): List<Movie> {
        val dbSearch = findSearchByPK(query) ?: return emptyList()
        if (isSearchExpired(dbSearch)) return emptyList()
        searchDao.updateInTx(dbSearch.apply { execDate = System.currentTimeMillis() })
        return dbSearch.movies
    }

    /**
     * Try to find last success search to restore UI state on launch
     */
    fun getLastSearch(): Search = searchDao.queryBuilder()
        .orderDesc(SearchDao.Properties.ExecDate)
        .list().firstOrNull() ?: Search()

    /**
     * Save movie info
     */
    fun saveMovieInfo(movieInfo: MovieInfoResponse) {
        val dbMovieInfo = findMovieInfoByPK(movieInfo.imdbID)
            ?: MovieInfo().apply { imdbID = movieInfo.imdbID }
        dbMovieInfo.apply {
            title = movieInfo.title
            year = movieInfo.year
            genre = movieInfo.genre
            director = movieInfo.director
            actors = movieInfo.actors
            plot = movieInfo.plot
            poster = movieInfo.poster
            metaScore = movieInfo.metaScore
            imdbRating = movieInfo.imdbRating
        }
        movieInfoDao.insertOrReplace(dbMovieInfo)
    }

    /**
     * Get movie info
     */
    fun getMovieInfo(imdbID: String): MovieInfo = findMovieInfoByPK(imdbID) ?: MovieInfo()

    /**
     * Return true if search executed more than 1 month ago
     */
    internal fun isSearchExpired(search: Search): Boolean {
        val currentTime = System.currentTimeMillis()
        return abs(currentTime - search.execDate) > expirationTime
    }

    private fun saveSearch(query: String): Search {
        var dbSearch = findSearchByPK(query)
        if (dbSearch == null) {
            dbSearch = Search().apply { this.query = query; execDate = System.currentTimeMillis() }
            searchDao.insert(dbSearch)
        } else {
            dbSearch.execDate = System.currentTimeMillis()
            searchDao.update(dbSearch)
        }
        return dbSearch
    }

    private fun saveMovies(movies: List<MovieEntry>): List<Long> {
        val res = ArrayList<Long>()
        movies.forEach {
            val dbMovie = findMovieByPK(it.imdbID) ?: Movie().apply { imdbID = it.imdbID }
            dbMovie.apply {
                title = it.title
                year = it.year
                type = it.type
                poster = it.poster
            }
            res.add(movieDao.insertOrReplace(dbMovie))
        }
        return res
    }

    private fun updateSearch2Movies(search: Search, moviesIds: List<Long>) {
        search2MovieDao.queryBuilder()
            .where(Search2MovieDao.Properties.SearchId.eq(search.id))
            .buildDelete()
            .executeDeleteWithoutDetachingEntities()
        moviesIds.forEach {
            val dbItem = Search2Movie().apply { searchId = search.id; movieId = it }
            search2MovieDao.insert(dbItem)
        }
        search.resetMovies()
    }

    private fun findSearchByPK(query: String): Search? = searchDao.queryBuilder()
        .where(SearchDao.Properties.Query.eq(query))
        .limit(1)
        .unique()

    private fun findMovieByPK(imdbID: String): Movie? = movieDao.queryBuilder()
        .where(MovieDao.Properties.ImdbID.eq(imdbID))
        .limit(1)
        .unique()

    private fun findMovieInfoByPK(imdbID: String): MovieInfo? = movieInfoDao.queryBuilder()
        .where(MovieInfoDao.Properties.ImdbID.eq(imdbID))
        .limit(1)
        .unique()

    /**
     * Delete all local saved users and queries
     */
    fun clear() = daoSession.runInTx {
        movieDao.deleteAll()
        movieInfoDao.deleteAll()
        searchDao.deleteAll()
        search2MovieDao.deleteAll()
    }

}