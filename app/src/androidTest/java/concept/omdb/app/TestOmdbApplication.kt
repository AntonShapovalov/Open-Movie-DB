package concept.omdb.app

import concept.omdb.di.AppComponent
import concept.omdb.di.DaggerTestAppComponent
import concept.omdb.di.TestAppComponent

/**
 * Provides [TestAppComponent] dependencies for UI testing
 */
class TestOmdbApplication : OmdbApplication() {

    override val appComponent: AppComponent by lazy {
        DaggerTestAppComponent.factory().create()
    }

    val testAppComponent: TestAppComponent get() = appComponent as TestAppComponent

}