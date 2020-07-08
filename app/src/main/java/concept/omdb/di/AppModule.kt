package concept.omdb.di

import concept.omdb.app.SchedulerProvider
import concept.omdb.data.repo.MovieDataRepository
import concept.omdb.data.repo.MovieRepository
import concept.omdb.ui.activity.UISchedulers
import dagger.Binds
import dagger.Module

/**
 * Provides app scope dependencies, like data repository and rx schedulers
 */
@Suppress("unused")
@Module
abstract class AppModule {

    @Binds
    abstract fun provideMovieRepository(repository: MovieDataRepository): MovieRepository

    @Binds
    abstract fun provideSchedulers(schedulers: UISchedulers): SchedulerProvider

}