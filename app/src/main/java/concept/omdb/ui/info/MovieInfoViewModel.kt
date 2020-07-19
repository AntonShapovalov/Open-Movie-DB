package concept.omdb.ui.info

import concept.omdb.ui.activity.MovieInfoData
import concept.omdb.ui.activity.MovieViewModel

/**
 * View Model for [MovieInfoFragment]
 */
class MovieInfoViewModel : MovieViewModel() {

    private var imdbID = ""

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
        val d = repository.getMovieInfo(imdbID)
            .map { MovieInfoData(it) }
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.main)
            .doFinally { progress.postValue(false) }
            .subscribe({ onResult(it) }, { onError(it) })
        compositeDisposable.add(d)
    }

}