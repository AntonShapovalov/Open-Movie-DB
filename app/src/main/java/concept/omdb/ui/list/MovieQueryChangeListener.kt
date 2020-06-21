package concept.omdb.ui.list

import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.FloatingSearchView.OnQueryChangeListener

class MovieQueryChangeListener(
    private val searchView: FloatingSearchView,
    private val viewModel: MovieListViewModel
) : OnQueryChangeListener {

    private var currentSuggestions: List<MovieSuggestion> = emptyList()

    override fun onSearchTextChanged(oldQuery: String?, newQuery: String?) {
        if (newQuery != null && newQuery.length > 1) {
            val suggestions = viewModel.filterSuggestions(newQuery)
            if (suggestions.isEmpty()) {
                currentSuggestions = emptyList()
                searchView.clearSuggestions()
            } else if (currentSuggestions.sortedBy { it.query } != suggestions) {
                currentSuggestions = suggestions
                searchView.swapSuggestions(suggestions)
            }
        } else {
            currentSuggestions = emptyList()
            searchView.clearSuggestions()
        }
    }

}