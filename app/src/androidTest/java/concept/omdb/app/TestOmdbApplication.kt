package concept.omdb.app

import concept.omdb.di.AppComponent
import concept.omdb.di.DaggerTestAppComponent
import concept.omdb.di.TestAppModule

class TestOmdbApplication : OmdbApplication() {

    override fun initAppComponent(): AppComponent = DaggerTestAppComponent.builder()
        .testAppModule(TestAppModule())
        .build()

}