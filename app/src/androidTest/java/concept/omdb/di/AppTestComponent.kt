package concept.omdb.di

import concept.omdb.data.repo.LocalDataSourceAndroidTest
import dagger.Component
import javax.inject.Singleton

/**
 * Provides dependencies for [LocalDataSourceAndroidTest]
 */
@Singleton
@Component(modules = [AppModule::class])
interface AppTestComponent {

    fun inject(test: LocalDataSourceAndroidTest)

}