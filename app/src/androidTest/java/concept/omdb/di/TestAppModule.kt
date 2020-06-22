package concept.omdb.di

import concept.omdb.ui.activity.SchedulerProvider
import concept.omdb.ui.activity.TestSchedulerProvider
import concept.omdb.data.repo.FakeMovieRepository
import concept.omdb.data.repo.MovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Provides fake data for UI testing
 */
@Module
class TestAppModule {

    @Singleton
    @Provides
    fun provideFakeMovieRepository(): MovieRepository {
        return FakeMovieRepository()
    }

    @Singleton
    @Provides
    fun provideTestSchedulers(): SchedulerProvider {
        return TestSchedulerProvider()
    }

}