package concept.omdb.app

import android.app.Application
import concept.omdb.BuildConfig
import concept.omdb.di.ApiModule
import concept.omdb.di.AppComponent
import concept.omdb.di.AppModule
import concept.omdb.di.DaggerAppComponent
import timber.log.Timber

class OMDBApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .apiModule(ApiModule())
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

}