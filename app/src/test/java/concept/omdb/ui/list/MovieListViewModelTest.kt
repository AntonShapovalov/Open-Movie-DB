package concept.omdb.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import concept.omdb.data.repo.FakeTestRepository
import concept.omdb.ui.activity.LastSearchData
import concept.omdb.ui.activity.MovieData
import concept.omdb.ui.activity.MovieListData
import concept.omdb.ui.activity.TestSchedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Unit test for [MovieListViewModel]
 *
 * Test works with fake data from [FakeTestRepository]
 */
class MovieListViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val fakeRepository = FakeTestRepository()
    private val testSchedulers = TestSchedulers()
    private lateinit var viewModel: MovieListViewModel
    private lateinit var latch: CountDownLatch

    @Before
    fun setUp() {
        viewModel = MovieListViewModel().apply {
            repository = fakeRepository
            schedulers = testSchedulers
        }
        latch = CountDownLatch(1)
    }

    @Test
    fun loadLastSearch() {
        val observer = Observer<MovieData> { data ->
            Assert.assertTrue(data is LastSearchData)
            val search = (data as LastSearchData).search
            Assert.assertEquals(search, fakeRepository.search)
            latch.countDown()
        }
        viewModel.data.observeForever(observer)
        viewModel.loadLastSearch()
        latch.await(1, TimeUnit.SECONDS)
        viewModel.data.removeObserver(observer)
    }

    @Test
    fun getMovies() {
        val observer = Observer<MovieData> { data ->
            Assert.assertTrue(data is MovieListData)
            val list = (data as MovieListData).list
            Assert.assertEquals(list, fakeRepository.movieList)
            latch.countDown()
        }
        viewModel.data.observeForever(observer)
        viewModel.getMovies("")
        latch.await(1, TimeUnit.SECONDS)
        viewModel.data.removeObserver(observer)
    }

}