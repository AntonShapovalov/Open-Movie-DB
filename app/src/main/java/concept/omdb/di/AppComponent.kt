package concept.omdb.di

import dagger.Component
import javax.inject.Singleton

/**
 * Provide Application scope dependencies
 */
@Singleton
@Component(modules = [AppModule::class, ApiModule::class])
interface AppComponent {

//    fun inject(gridViewModel: GridViewModel)

}