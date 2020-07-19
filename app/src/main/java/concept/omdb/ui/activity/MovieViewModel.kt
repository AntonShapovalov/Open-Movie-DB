package concept.omdb.ui.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import concept.omdb.app.SchedulerProvider
import concept.omdb.data.repo.MovieRepository
import concept.omdb.ui.info.MovieInfoViewModel
import concept.omdb.ui.list.MovieListViewModel
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

/**
 * Common logic for [MovieListViewModel] and [MovieInfoViewModel]
 */
abstract class MovieViewModel : ViewModel() {

    @Inject lateinit var repository: MovieRepository
    @Inject lateinit var schedulers: SchedulerProvider

    val data = MutableLiveData<MovieData>()
    val progress = MutableLiveData<Boolean>()

    protected var compositeDisposable = CompositeDisposable()
    protected val isProgress get() = progress.value == true

    protected fun onResult(value: MovieData) {
        Timber.d("data -> %s", value.javaClass.simpleName)
        data.value = value
    }

    protected fun onError(t: Throwable) {
        Timber.e(t)
        onResult(MovieDataError(t))
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}