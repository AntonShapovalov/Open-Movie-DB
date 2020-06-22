package concept.omdb.app

import concept.omdb.di.AppComponent
import concept.omdb.di.DaggerTestAppComponent
import concept.omdb.di.TestAppComponent
import concept.omdb.di.TestAppModule

/**
 * Provides [TestAppComponent] dependencies for UI testing
 */
class TestOmdbApplication : OmdbApplication() {

    val testAppComponent: TestAppComponent get() = appComponent as TestAppComponent

    override fun initAppComponent(): AppComponent = DaggerTestAppComponent.builder()
        .testAppModule(TestAppModule())
        .build()

}