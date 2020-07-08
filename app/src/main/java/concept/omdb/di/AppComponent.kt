package concept.omdb.di

import android.content.Context
import concept.omdb.ui.info.MovieInfoViewModel
import concept.omdb.ui.list.MovieListViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Provide Application scope dependencies
 */
@Singleton
@Component(modules = [DbModule::class, ApiModule::class, AppModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(movieListViewModel: MovieListViewModel)

    fun inject(movieInfoViewModel: MovieInfoViewModel)

}