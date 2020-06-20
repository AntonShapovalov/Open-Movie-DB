package concept.omdb.di

import concept.omdb.ui.info.MovieInfoViewModel
import concept.omdb.ui.list.MovieListViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Provide Application scope dependencies
 */
@Singleton
@Component(modules = [AppModule::class, ApiModule::class])
interface AppComponent {

    fun inject(movieListViewModel: MovieListViewModel)

    fun inject(movieInfoViewModel: MovieInfoViewModel)

}