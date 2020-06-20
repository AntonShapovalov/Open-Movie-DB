package concept.omdb.ui.activity

import android.view.View
import androidx.fragment.app.FragmentActivity
import concept.omdb.app.OMDBApplication
import concept.omdb.data.dao.Movie
import concept.omdb.di.AppComponent

val FragmentActivity.appComponent: AppComponent get() = (application as OMDBApplication).appComponent

sealed class MovieData
data class MovieListData(val list: List<Movie>) : MovieData()
data class MovieErrorData(val throwable: Throwable) : MovieData()

fun View.showOrHide(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
}