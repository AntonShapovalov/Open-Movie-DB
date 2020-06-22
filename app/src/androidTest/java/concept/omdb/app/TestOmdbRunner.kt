package concept.omdb.app

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class TestOmdbRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, TestOmdbApplication::class.java.name, context)
    }

}