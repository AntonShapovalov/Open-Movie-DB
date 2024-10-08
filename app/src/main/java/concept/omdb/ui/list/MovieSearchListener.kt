package concept.omdb.ui.list

import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.FloatingSearchView.OnSearchListener
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion

class MovieSearchListener(
    private val searchView: FloatingSearchView,
    private val viewModel: MovieListViewModel
) : OnSearchListener {

    override fun onSearchAction(query: String?) {
        if (query == null || query.isEmpty() || query.isBlank()) return
        viewModel.getMovies(query)
    }

    override fun onSuggestionClicked(searchSuggestion: SearchSuggestion?) {
        searchSuggestion?.body?.takeIf { it.isNotEmpty() }?.let {
            searchView.setSearchText(it)
            searchView.setSearchFocused(false)
            viewModel.getMovies(it)
        }
    }

}