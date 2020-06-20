package concept.omdb.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import concept.omdb.data.repo.MovieRepository
import concept.omdb.ui.activity.MovieData
import concept.omdb.ui.activity.MovieErrorData
import concept.omdb.ui.activity.MovieListData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * View Model for [MovieListFragment]
 */
class MovieListViewModel : ViewModel() {

    @Inject lateinit var repository: MovieRepository

    var query = ""
    val data = MutableLiveData<MovieData>()
    val progress = MutableLiveData<Boolean>()

    private var disposable = Disposables.disposed()

    /**
     * Load list of movies based on title
     */
    fun getMovies(title: String) {
        if (!disposable.isDisposed) return
        query = title
        disposable = repository.getMovies(title)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progress.postValue(true) }
            .doFinally { progress.postValue(false) }
            .subscribe({ data.value = MovieListData(it) }, { data.value = MovieErrorData(it) })
    }

    /**
     * Reload movies on error
     */
    fun reloadMovies() {
        getMovies(query)
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

}