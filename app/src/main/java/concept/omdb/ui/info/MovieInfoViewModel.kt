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
import javax.inject.Inject

/**
 * View Model for [MovieInfoFragment]
 */
class MovieInfoViewModel : ViewModel() {

    @Inject lateinit var repository: MovieRepository

    val data = MutableLiveData<MovieData>()
    val progress = MutableLiveData<Boolean>()

    private var imdbID = ""
    private var disposable = Disposables.disposed()

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
        if (!disposable.isDisposed) return
        disposable = repository.getMovieInfo(imdbID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progress.postValue(true) }
            .doFinally { progress.postValue(false) }
            .subscribe({ data.value = MovieInfoData(it) }, { data.value = MovieDataError(it) })
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

}