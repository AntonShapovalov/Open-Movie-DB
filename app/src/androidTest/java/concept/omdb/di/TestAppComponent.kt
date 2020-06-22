package concept.omdb.di

import dagger.Component
import javax.inject.Singleton

/**
 * Provides fake data for UI testing
 */
@Singleton
@Component(modules = [TestAppModule::class])
interface TestAppComponent : AppComponent