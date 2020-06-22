package concept.omdb.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import concept.omdb.ui.activity.SchedulerProvider
import concept.omdb.data.repo.MovieRepository
import concept.omdb.ui.activity.LastSearchData
import concept.omdb.ui.activity.MovieData
import concept.omdb.ui.activity.MovieDataError
import concept.omdb.ui.activity.MovieListData
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

/**
 * View Model for [MovieListFragment]
 */
class MovieListViewModel : ViewModel() {

    @Inject lateinit var repository: MovieRepository
    @Inject lateinit var schedulers: SchedulerProvider

    val data = MutableLiveData<MovieData>()
    val progress = MutableLiveData<Boolean>()

    private var query = ""
    private val suggestions = HashSet<MovieSuggestion>()
    private var compositeDisposable = CompositeDisposable()
    private val isProgress get() = progress.value == true

    /**
     * Get last executed search from local DB
     * Loads only once on screen start, otherwise, if [data] exists, just return
     */
    fun loadLastSearch() {
        if (data.value != null) return
        getLastSearch()
    }

    /**
     * Load all previous queries to use them as suggestions
     * Do not show progress, load it silently
     */
    fun loadSuggestions() {
        if (suggestions.isNotEmpty()) return
        getSuggestions()
    }

    /**
     * Filter all suggestions by search input
     */
    fun filterSuggestions(query: String): List<MovieSuggestion> = suggestions
        .filter { it.query.contains(query, ignoreCase = true) }
        .sortedBy { it.query }

    /**
     * Load movies, save query to reload data on error
     * Do not start loading if [data] exists and the query the same
     */
    fun getMovies(title: String) {
        if (title.isEmpty() || (title == query && data.value is MovieListData)) return
        query = title
        suggestions.add(MovieSuggestion(title))
        getMovies()
    }

    /**
     * Try to reload data on error
     */
    fun reloadMovies() {
        if (query.isEmpty()) return
        getMovies()
    }

    private fun getLastSearch() {
        if (isProgress) return else progress.value = true
        val d = repository.getLastSearch()
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.main)
            .doFinally { progress.postValue(false) }
            .subscribe({ onResult(LastSearchData(it)) }, { Timber.e(it) })
        compositeDisposable.add(d)
    }

    private fun getSuggestions() {
        val d = repository.getAllQueries()
            .map { query -> query.map { MovieSuggestion(it) } }
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.main)
            .subscribe({ suggestions.addAll(it) }, { Timber.e(it) })
        compositeDisposable.add(d)
    }

    private fun getMovies() {
        if (isProgress) return else progress.value = true
        val d = repository.getMovies(query)
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.main)
            .doFinally { progress.postValue(false) }
            .subscribe({ onResult(MovieListData(it)) }, { onResult(MovieDataError(it)) })
        compositeDisposable.add(d)
    }

    private fun onResult(value: MovieData) {
        if (value is MovieDataError) Timber.e(value.error)
        Timber.d("data -> %s", value.javaClass.simpleName)
        data.value = value
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}