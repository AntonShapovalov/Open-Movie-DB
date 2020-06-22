package concept.omdb.data.repo

import concept.omdb.data.api.MovieEntry
import concept.omdb.data.api.MovieInfoResponse
import concept.omdb.data.api.MovieNotFoundException
import concept.omdb.di.ApiModule
import concept.omdb.di.DaggerTestApiComponent
import io.reactivex.observers.TestObserver
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

/**
 * Test for [RemoteDataSource]
 * Run test to get it compiled!
 *
 * Test uses real network communication and dedicated for testing API model and retrofit API config
 */
class RemoteDataSourceTest {

    @Inject lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setUp() = DaggerTestApiComponent.builder()
        .apiModule(ApiModule())
        .build()
        .inject(this)

    @Test
    fun getImages() {
        val testObserver = TestObserver<List<MovieEntry>>()
        remoteDataSource.getMovies(title = "terminator")
            .doOnNext { list ->
                Assert.assertTrue(list.isNotEmpty())
                list.forEach {
                    Assert.assertTrue(it.imdbID.isNotEmpty())
                    println(it)
                }
            }.subscribe(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertComplete()
        testObserver.dispose()
    }

    @Test
    fun movieNotFound() {
        val testObserver = TestObserver<List<MovieEntry>>()
        remoteDataSource.getMovies(title = "movienotfound")
            .subscribe(testObserver)
        testObserver.assertFailure(MovieNotFoundException::class.java)
        testObserver.dispose()
    }

    @Test
    fun getImageInfo() {
        val testObserver = TestObserver<MovieInfoResponse>()
        remoteDataSource.getMovieInfo(imdbID = "tt0103064")
            .doOnNext {
                Assert.assertTrue(it.imdbID.isNotEmpty())
                println(it)
            }.subscribe(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertComplete()
        testObserver.dispose()
    }

}