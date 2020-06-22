package concept.omdb.ui.info

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import concept.omdb.data.repo.FakeTestRepository
import concept.omdb.ui.activity.MovieData
import concept.omdb.ui.activity.MovieInfoData
import concept.omdb.ui.activity.TestSchedulerProvider
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Unit test for [MovieInfoViewModel]
 *
 * Test works with fake data from [FakeTestRepository]
 */
class MovieInfoViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val fakeRepository = FakeTestRepository()
    private val testSchedulers = TestSchedulerProvider()
    private lateinit var viewModel: MovieInfoViewModel
    private lateinit var latch: CountDownLatch

    @Before
    fun setUp() {
        viewModel = MovieInfoViewModel().apply {
            repository = fakeRepository
            schedulers = testSchedulers
        }
        latch = CountDownLatch(1)
    }

    @Test
    fun getMoviesInfo() {
        val observer = Observer<MovieData> { data ->
            Assert.assertTrue(data is MovieInfoData)
            val info = (data as MovieInfoData).info
            Assert.assertEquals(info, fakeRepository.movieInfo)
            latch.countDown()
        }
        viewModel.data.observeForever(observer)
        viewModel.getMovieInfo("")
        latch.await(1, TimeUnit.SECONDS)
        viewModel.data.removeObserver(observer)
    }

}