package concept.omdb.ui.activity

import android.view.View
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import concept.omdb.R
import concept.omdb.app.OmdbApplication
import concept.omdb.data.dao.Movie
import concept.omdb.data.dao.MovieInfo
import concept.omdb.data.dao.Search
import concept.omdb.di.AppComponent
import timber.log.Timber

sealed class MovieData
data class MovieListData(val list: List<Movie>) : MovieData()
data class MovieInfoData(val info: MovieInfo) : MovieData()
data class LastSearchData(val search: Search) : MovieData()
data class MovieDataError(val error: Throwable) : MovieData()

val FragmentActivity.appComponent: AppComponent get() = (application as OmdbApplication).appComponent

fun View.showOrHide(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
}

fun View.showErrorAction(error: Throwable, action: (View) -> Unit) {
    errorSnackbar(error).setAction(R.string.text_retry, action).show()
}

fun View.showErrorInfo(error: Throwable) {
    errorSnackbar(error).setAction(android.R.string.ok) {}.show()
}

private fun View.errorSnackbar(error: Throwable): Snackbar {
    Timber.e(error)
    val text = error.localizedMessage
    return Snackbar.make(this, text ?: "Unknown error", Snackbar.LENGTH_INDEFINITE)
}

