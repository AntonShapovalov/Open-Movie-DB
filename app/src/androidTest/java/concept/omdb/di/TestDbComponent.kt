package concept.omdb.di

import concept.omdb.data.repo.LocalDataSourceAndroidTest
import dagger.Component
import javax.inject.Singleton

/**
 * Provides Android context dependencies for [LocalDataSourceAndroidTest]
 */
@Singleton
@Component(modules = [AppModule::class])
interface TestDbComponent {

    fun inject(test: LocalDataSourceAndroidTest)

}