package concept.omdb.di

import concept.omdb.data.repo.RemoteStorageTest
import dagger.Component
import javax.inject.Singleton

/**
 * Provide dependencies for [RemoteStorageTest]
 */
@Singleton
@Component(modules = [ApiModule::class])
interface ApiTestComponent {

    fun inject(test: RemoteStorageTest)

}