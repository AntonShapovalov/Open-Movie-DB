package concept.omdb.ui.activity

import android.view.View
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import concept.omdb.R
import concept.omdb.app.OMDBApplication
import concept.omdb.data.dao.Movie
import concept.omdb.data.dao.MovieInfo
import concept.omdb.di.AppComponent
import timber.log.Timber

val FragmentActivity.appComponent: AppComponent get() = (application as OMDBApplication).appComponent

sealed class MovieData
data class MovieListData(val list: List<Movie>) : MovieData()
data class MovieInfoData(val info: MovieInfo) : MovieData()
data class MovieErrorData(val throwable: Throwable) : MovieData()

fun View.showOrHide(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
}

fun View.showError(throwable: Throwable, action: (View) -> Unit) {
    Timber.e(throwable)
    val text = throwable.localizedMessage
    Snackbar.make(this, text ?: "Unknown error", Snackbar.LENGTH_INDEFINITE)
        .setAction(R.string.text_retry, action)
        .show()
}