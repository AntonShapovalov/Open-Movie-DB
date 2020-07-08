package concept.omdb.app

import android.app.Application
import concept.omdb.BuildConfig
import concept.omdb.di.AppComponent
import concept.omdb.di.DaggerAppComponent
import timber.log.Timber

open class OmdbApplication : Application() {

    open val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

}