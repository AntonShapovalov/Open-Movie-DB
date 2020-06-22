package concept.omdb.ui.list

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import concept.omdb.R
import concept.omdb.app.TestOmdbApplication
import concept.omdb.data.repo.FakeAndroidTestRepository
import concept.omdb.data.repo.MovieRepository
import concept.omdb.di.TestAppComponent
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * Android test for [MovieListFragment]
 *
 * Test works with fake data from [FakeAndroidTestRepository],
 * that is provided by [TestAppComponent] via [TestOmdbApplication]
 */
@RunWith(AndroidJUnit4::class)
class MovieListFragmentAndroidTest {

    @Inject lateinit var repository: MovieRepository
    private val fakeRepository: FakeAndroidTestRepository by lazy { repository as FakeAndroidTestRepository }

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<TestOmdbApplication>()
        context.testAppComponent.inject(this)
    }

    @Test
    fun displayMoviesListOnStart() {
        launchFragmentInContainer<MovieListFragment>(themeResId = R.style.AppTheme)
        val search = fakeRepository.search
        val movie = fakeRepository.movieList.first()
        onView(withResourceName("search_bar_text")).check(matches(withText(search.query)))
        onView(withId(R.id.textViewTitle)).check(matches(withText(movie.title)))
        onView(withId(R.id.textViewYear)).check(matches(withText(movie.year)))
        onView(withId(R.id.textViewType)).check(matches(withText(movie.type)))
    }

}