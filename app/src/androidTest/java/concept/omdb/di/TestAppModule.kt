package concept.omdb.di

import concept.omdb.app.SchedulerProvider
import concept.omdb.data.repo.FakeAndroidTestRepository
import concept.omdb.data.repo.MovieRepository
import concept.omdb.ui.activity.AndroidTestSchedulers
import dagger.Binds
import dagger.Module

/**
 * Provides fake data for UI testing
 */
@Suppress("unused")
@Module
abstract class TestAppModule {

    @Binds
    abstract fun provideFakeRepository(repository: FakeAndroidTestRepository): MovieRepository

    @Binds
    abstract fun provideTestSchedulers(schedulers: AndroidTestSchedulers): SchedulerProvider

}