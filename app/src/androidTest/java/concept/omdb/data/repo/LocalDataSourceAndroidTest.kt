package concept.omdb.data.repo

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import concept.omdb.data.api.MovieEntry
import concept.omdb.data.api.MovieInfoResponse
import concept.omdb.data.dao.DaoSession
import concept.omdb.data.dao.Search
import concept.omdb.di.AppModule
import concept.omdb.di.DaggerDbTestComponent
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Android context test for [LocalDataSource]
 * Run test to get it compiled!
 *
 * Test uses real SQL database on device (or emulator) to check logic of storing
 */
@RunWith(AndroidJUnit4::class)
class LocalDataSourceAndroidTest {

    @Inject lateinit var localDataSource: LocalDataSource
    @Inject lateinit var daoSession: DaoSession

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
        DaggerDbTestComponent.builder()
            .appModule(AppModule(context))
            .build()
            .inject(this)
        localDataSource.clear()
    }

    @Test
    fun saveMovies() {
        // init
        val query = "test query"
        val title = "test title"
        val year = "test year"
        val imdbID = "test IMDB"
        val type = "test type"
        val poster = "test poster"
        val response = listOf(
            MovieEntry(
                title = title, year = year, imdbID = imdbID, type = type, poster = poster
            )
        )
        localDataSource.saveMovies(query = query, movies = response)

        // check movies' values
        val movies = localDataSource.getMovies(query = query)
        Assert.assertEquals(1, movies.size)
        val movie = movies[0]
        Assert.assertEquals(title, movie.title)
        Assert.assertEquals(year, movie.year)
        Assert.assertEquals(imdbID, movie.imdbID)
        Assert.assertEquals(type, movie.type)
        Assert.assertEquals(poster, movie.poster)
    }

    @Test
    fun lastSearch() {
        // init
        val firstQuery = "first query"
        val secondQuery = "last query"
        val title = "test title"
        val year = "test year"
        val imdbID = "test imdbID"
        val type = "test type"
        val poster = "test poster"
        val response = listOf(
            MovieEntry(
                title = title, year = year, imdbID = imdbID, type = type, poster = poster
            )
        )

        // first query
        localDataSource.saveMovies(query = firstQuery, movies = response)
        with(daoSession) {
            Assert.assertEquals(1, movieDao.loadAll().size)
            Assert.assertEquals(1, searchDao.loadAll().size)
            Assert.assertEquals(1, search2MovieDao.loadAll().size)
        }

        // second query but the same movies' list
        localDataSource.saveMovies(query = secondQuery, movies = response)
        with(daoSession) {
            Assert.assertEquals(1, movieDao.loadAll().size)
            Assert.assertEquals(2, searchDao.loadAll().size)
            Assert.assertEquals(2, search2MovieDao.loadAll().size)
        }
        Assert.assertEquals(secondQuery, localDataSource.getLastSearch())
    }

    @Test
    fun saveMovieInfo() {
        // init
        val title = "test title"
        val year = "test year"
        val genre = "test genre"
        val director = "test director"
        val actors = "test actors"
        val plot = "test plot"
        val poster = "test poster"
        val metaScore = "test meta score"
        val imdbRating = "test rating"
        val imdbID = "test imdbID"
        val response = MovieInfoResponse(
            title = title,
            year = year,
            genre = genre,
            director = director,
            actors = actors,
            plot = plot,
            poster = poster,
            metaScore = metaScore,
            imdbRating = imdbRating,
            imdbID = imdbID
        )
        localDataSource.saveMovieInfo(response)

        // check info values
        val movieInfo = localDataSource.getMovieInfo(imdbID)
        Assert.assertNotNull(movieInfo)
        Assert.assertEquals(title, movieInfo.title)
        Assert.assertEquals(year, movieInfo.year)
        Assert.assertEquals(genre, movieInfo.genre)
        Assert.assertEquals(director, movieInfo.director)
        Assert.assertEquals(actors, movieInfo.actors)
        Assert.assertEquals(plot, movieInfo.plot)
        Assert.assertEquals(poster, movieInfo.poster)
        Assert.assertEquals(metaScore, movieInfo.metaScore)
        Assert.assertEquals(imdbRating, movieInfo.imdbRating)
        Assert.assertEquals(imdbID, movieInfo.imdbID)
    }

    @Test
    fun isSearchExpired() {
        // not expired search
        var time = System.currentTimeMillis()
        val search = Search(1, "test query", time)
        Assert.assertFalse(localDataSource.isSearchExpired(search))
        // expired search
        time -= (localDataSource.expirationTime + TimeUnit.DAYS.toMillis(1))
        search.execDate = time
        Assert.assertTrue(localDataSource.isSearchExpired(search))
    }

    @After
    fun tearDown() = localDataSource.clear()

}