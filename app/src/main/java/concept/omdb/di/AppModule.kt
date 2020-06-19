package concept.omdb.di

import android.content.Context
import concept.omdb.data.dao.DaoMaster
import concept.omdb.data.dao.DaoSession
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Provide local (DB, context) dependencies for [AppComponent]
 */
@Module
class AppModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context = context

    @Singleton
    @Provides
    fun provideDaoSession(): DaoSession {
        val db = DaoMaster.DevOpenHelper(context, "movie-db").writableDb
        return DaoMaster(db).newSession()
    }

}