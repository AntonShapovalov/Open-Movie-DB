package concept.omdb.di

import android.content.Context
import concept.omdb.data.repo.LocalDataSourceAndroidTest
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Provides Android context dependencies for [LocalDataSourceAndroidTest]
 */
@Singleton
@Component(modules = [DbModule::class])
interface TestDbComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): TestDbComponent
    }

    fun inject(test: LocalDataSourceAndroidTest)

}