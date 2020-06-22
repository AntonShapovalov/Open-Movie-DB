package concept.omdb.di

import concept.omdb.data.repo.FakeMovieRepository
import concept.omdb.ui.list.MovieListFragmentTest
import dagger.Component
import javax.inject.Singleton

/**
 * Provides fake [FakeMovieRepository] for UI testing
 */
@Singleton
@Component(modules = [TestAppModule::class])
interface TestAppComponent : AppComponent {

    fun inject(test: MovieListFragmentTest)

}