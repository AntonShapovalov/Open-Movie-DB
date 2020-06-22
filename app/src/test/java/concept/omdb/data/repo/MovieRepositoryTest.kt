package concept.omdb.data.repo

import concept.omdb.data.api.MovieInfoResponse
import concept.omdb.data.dao.Movie
import concept.omdb.data.dao.MovieInfo
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

/**
 * Test for [MovieRepository]
 *
 * Since [LocalDataSource] and [RemoteDataSource] are covered by dedicated tests
 * [LocalDataSourceAndroidTest] and [RemoteDataSourceTest], just mock them
 */
class MovieRepositoryTest {

    private lateinit var localData: LocalDataSource
    private lateinit var remoteData: RemoteDataSource
    private val repository: MovieRepository = MovieRepository()
    private val dummy = "dummy"
    private val movieList = listOf(Movie())
    private val movieInfo = MovieInfo().apply { imdbID = "1" }

    @Before
    fun setUp() {
        localData = mock(LocalDataSource::class.java)
        remoteData = mock(RemoteDataSource::class.java)
        repository.localData = localData
        repository.remoteData = remoteData
    }

    @Test
    fun getRemoteMoviesIfLocalIsEmpty() {
        `when`(localData.getMovies(dummy)).thenReturn(emptyList(), movieList)
        `when`(remoteData.getMovies(title = dummy)).thenReturn(Observable.just(emptyList()))
        repository_getMovies()
        verify(localData, times(2)).getMovies(dummy)
        verify(remoteData).getMovies(title = dummy)
    }

    @Test
    fun getLocalMovies() {
        `when`(localData.getMovies(dummy)).thenReturn(movieList)
        repository_getMovies()
        verify(localData).getMovies(dummy)
        verify(remoteData, never()).getMovies(title = dummy)
    }

    @Test
    fun getRemoteMovieInfoIfLocalIsEmpty() {
        `when`(localData.getMovieInfo(dummy)).thenReturn(MovieInfo(), movieInfo)
        `when`(remoteData.getMovieInfo(imdbID = dummy))
            .thenReturn(Observable.just(MovieInfoResponse("", "", "", "", "", "", "", "", "", "")))
        repository_getMovieInfo()
        verify(localData, times(2)).getMovieInfo(dummy)
        verify(remoteData).getMovieInfo(imdbID = dummy)
    }

    @Test
    fun getLocalMovieInfo() {
        `when`(localData.getMovieInfo(dummy)).thenReturn(movieInfo)
        repository_getMovieInfo()
        verify(localData).getMovieInfo(dummy)
        verify(remoteData, never()).getMovieInfo(imdbID = dummy)
    }

    private fun repository_getMovies() {
        val testObserver = TestObserver<List<Movie>>()
        repository.getMovies(dummy)
            .doOnNext { Assert.assertEquals(movieList, it) }
            .subscribe(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertComplete()
    }

    private fun repository_getMovieInfo() {
        val testObserver = TestObserver<MovieInfo>()
        repository.getMovieInfo(dummy)
            .doOnNext { Assert.assertEquals(movieInfo, it) }
            .subscribe(testObserver)
        testObserver.assertNoErrors()
        testObserver.assertComplete()
    }

}