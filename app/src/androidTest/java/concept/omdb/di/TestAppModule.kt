package concept.omdb.di

import concept.omdb.app.SchedulerProvider
import concept.omdb.app.TestSchedulerProvider
import concept.omdb.data.repo.FakeMovieRepository
import concept.omdb.data.repo.MovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Provide fake data for UI testing
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