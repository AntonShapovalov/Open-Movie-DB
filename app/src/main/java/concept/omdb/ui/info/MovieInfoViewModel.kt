package concept.omdb.ui.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import concept.omdb.data.repo.MovieRepository
import concept.omdb.ui.activity.MovieData
import concept.omdb.ui.activity.MovieDataError
import concept.omdb.ui.activity.MovieInfoData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * View Model for [MovieInfoFragment]
 */
class MovieInfoViewModel : ViewModel() {

    @Inject lateinit var repository: MovieRepository

    val data = MutableLiveData<MovieData>()
    val progress = MutableLiveData<Boolean>()

    private var imdbID = ""
    private var disposable = Disposables.empty()
    private val isProgress get() = progress.value == true

    /**
     * Initial loading, save imdbID to reload data on error
     * Load data once only, if [data] is not empty - just return
     */
    fun getMovieInfo(imdbID: String) {
        if (data.value != null || imdbID.isEmpty()) return
        this.imdbID = imdbID
        getMovieInfo()
    }

    /**
     * Reload info on error
     */
    fun reloadMovieInfo() {
        if (imdbID.isEmpty()) return
        getMovieInfo()
    }

    /**
     * Load movie's info based on imdbID
     */
    private fun getMovieInfo() {
        if (isProgress) return else progress.value = true
        disposable = repository.getMovieInfo(imdbID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progress.postValue(false) }
            .subscribe({ onResult(MovieInfoData(it)) }, { onResult(MovieDataError(it), it) })
    }

    private fun onResult(value: MovieData, error: Throwable? = null) {
        if (error != null) Timber.e(error)
        Timber.d("data -> %s", value.javaClass.simpleName)
        data.value = value
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

}