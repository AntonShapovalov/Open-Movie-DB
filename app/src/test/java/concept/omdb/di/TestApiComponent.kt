package concept.omdb.di

import concept.omdb.data.repo.RemoteDataSourceTest
import dagger.Component
import javax.inject.Singleton

/**
 * Provide dependencies for [RemoteDataSourceTest]
 */
@Singleton
@Component(modules = [ApiModule::class])
interface TestApiComponent {

    fun inject(test: RemoteDataSourceTest)

}