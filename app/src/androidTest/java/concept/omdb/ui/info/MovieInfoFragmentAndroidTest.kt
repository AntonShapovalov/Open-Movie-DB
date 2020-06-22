package concept.omdb.ui.info

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import concept.omdb.R
import concept.omdb.app.TestOmdbApplication
import concept.omdb.data.repo.FakeMovieRepository
import concept.omdb.data.repo.MovieRepository
import concept.omdb.di.TestAppComponent
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

/**
 * Test for [MovieInfoFragment]
 *
 * Test works with fake data from [FakeMovieRepository],
 * that is provided by [TestAppComponent] via [TestOmdbApplication]
 */
class MovieInfoFragmentAndroidTest {

    @Inject lateinit var repository: MovieRepository
    private val fakeRepository: FakeMovieRepository by lazy { repository as FakeMovieRepository }

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<TestOmdbApplication>()
        context.testAppComponent.inject(this)
    }

    @Test
    fun displayMovieInfo() {
        val info = fakeRepository.movieInfo
        val bundle = MovieInfoFragmentArgs(info.imdbID).toBundle()
        launchFragmentInContainer<MovieInfoFragment>(bundle, R.style.AppTheme)
        onView(withId(R.id.textViewTitle)).check(matches(withText(info.title)))
        onView(withId(R.id.textViewYear)).check(matches(withText(info.year)))
        onView(withId(R.id.textViewGenre)).check(matches(withText(info.genre)))
        onView(withId(R.id.textViewDirector)).check(matches(withText(info.director)))
        onView(withId(R.id.textViewActors)).check(matches(withText(info.actors)))
        onView(withId(R.id.textViewPlot)).check(matches(withText(info.plot)))
        onView(withId(R.id.textViewMetaScore)).check(matches(withText(info.metaScore)))
        onView(withId(R.id.textViewImdbRating)).check(matches(withText(info.imdbRating)))
    }

}