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

    val data = MutableLiveData<MovieData>()
    val progress = MutableLiveData<Boolean>()

    private var query = ""
    private var disposable = Disposables.disposed()

    /**
     * Load movies, save query to reload data on error
     * Do not start loading if [data] exist and the query the same
     */
    fun getMovies(title: String) {
        if (title.isEmpty() || (title == query && data.value is MovieListData)) return
        query = title
        getMovies()
    }

    /**
     * Try to reload data on error
     */
    fun reloadMovies() {
        if (query.isEmpty()) return
        getMovies()
    }

    /**
     * Load list of movies based on [query]
     */
    private fun getMovies() {
        if (!disposable.isDisposed) return
        disposable = repository.getMovies(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progress.postValue(true) }
            .doFinally { progress.postValue(false) }
            .subscribe({ data.value = MovieListData(it) }, { data.value = MovieErrorData(it) })
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

}