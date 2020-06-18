package concept.omdb.data.repo

import concept.omdb.data.api.MovieEntry
import concept.omdb.data.api.MovieInfoResponse
import concept.omdb.di.ApiModule
import concept.omdb.di.DaggerApiTestComponent
import io.reactivex.observers.TestObserver
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

/**
 * Test for [RemoteStorage]
 */
class RemoteStorageTest {

    @Inject
    lateinit var remoteStorage: RemoteStorage

    private var testTitle = "terminator"
    private var testImdbID = "tt0103064"

    @Before
    fun setUp() = DaggerApiTestComponent.builder()
        .apiModule(ApiModule())
        .build()
        .inject(this)

    @Test
    fun getImages() {
        val testObserver = TestObserver<List<MovieEntry>>()
        remoteStorage.getMovies(title = testTitle)
            .doOnNext { list ->
                Assert.assertTrue(list.isNotEmpty())
                list.forEach {
                    Assert.assertTrue(it.imdbID.isNotEmpty())
                    testImdbID = it.imdbID
                    println(it)
                }
            }.subscribe(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertComplete()
    }

    @Test
    fun getImageInfo() {
        val testObserver = TestObserver<MovieInfoResponse>()
        remoteStorage.getMovieInfo(imdbID = testImdbID)
            .doOnNext {
                Assert.assertTrue(it.imdbID.isNotEmpty())
                println(it)
            }.subscribe(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertComplete()
    }

}