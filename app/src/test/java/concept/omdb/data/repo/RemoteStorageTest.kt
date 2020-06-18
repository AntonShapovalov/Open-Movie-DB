package concept.omdb.data.repo

import concept.omdb.data.api.MovieEntry
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

    @Before
    fun setUp() = DaggerApiTestComponent.builder()
        .apiModule(ApiModule())
        .build()
        .inject(this)

    @Test
    fun getImages() {
        val testObserver = TestObserver<List<MovieEntry>>()
        remoteStorage.getMovies(title = "terminator")
            .doOnNext { list ->
                Assert.assertTrue(list.isNotEmpty())
                list.forEach {
                    Assert.assertFalse(it.imdbID.isEmpty())
                    println(it)
                }
            }.subscribe(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertComplete()
    }

}