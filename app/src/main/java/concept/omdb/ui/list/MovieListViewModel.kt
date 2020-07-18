package concept.omdb.ui.list

import concept.omdb.ui.activity.LastSearchData
import concept.omdb.ui.activity.MovieDataError
import concept.omdb.ui.activity.MovieListData
import concept.omdb.ui.activity.MovieViewModel
import timber.log.Timber

/**
 * View Model for [MovieListFragment]
 */
class MovieListViewModel : MovieViewModel() {

    private var query = ""
    private val suggestions = HashSet<MovieSuggestion>()

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
            .subscribe({ onResult(MovieListData(it)) }, { onError(MovieDataError(it), it) })
        compositeDisposable.add(d)
    }

}