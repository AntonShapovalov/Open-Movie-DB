package concept.omdb.di

import concept.omdb.data.repo.FakeMovieRepository
import concept.omdb.ui.info.MovieInfoFragmentAndroidTest
import concept.omdb.ui.list.MovieListFragmentAndroidTest
import dagger.Component
import javax.inject.Singleton

/**
 * Provides fake [FakeMovieRepository] for UI testing
 */
@Singleton
@Component(modules = [TestAppModule::class])
interface TestAppComponent : AppComponent {

    fun inject(test: MovieListFragmentAndroidTest)

    fun inject(test: MovieInfoFragmentAndroidTest)

}