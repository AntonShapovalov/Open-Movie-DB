package concept.omdb.di

import android.content.Context
import concept.omdb.data.dao.DaoMaster
import concept.omdb.data.dao.DaoSession
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Provides local DB dependencies for [AppComponent]
 */
@Module
class DbModule {

    @Singleton
    @Provides
    fun provideDaoSession(context: Context): DaoSession {
        val db = DaoMaster.DevOpenHelper(context, "movie-db").writableDb
        return DaoMaster(db).newSession()
    }

}