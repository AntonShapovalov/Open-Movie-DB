package concept.omdb.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import concept.omdb.R
import concept.omdb.data.dao.Search
import concept.omdb.ui.activity.*
import kotlinx.android.synthetic.main.fragment_list.*
import timber.log.Timber

class MovieListFragment : Fragment() {

    private val viewModel by viewModels<MovieListViewModel>()
    private val adapter = MovieListAdapter { showMovieInfo(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        layoutManager.isSmoothScrollbarEnabled = true
        moviesList.layoutManager = layoutManager
        moviesList.adapter = adapter
        with(floatingSearchView) {
            setOnSearchListener(MovieSearchListener(this, viewModel))
            setOnQueryChangeListener(MovieQueryChangeListener(this, viewModel))
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(viewModel) {
            activity?.appComponent?.inject(this)
            progress.observe(viewLifecycleOwner, Observer { progressBar.showOrHide(it) })
            data.observe(viewLifecycleOwner, Observer { updateUI(it) })
            loadLastSearch()
            loadSuggestions()
        }
    }

    private fun updateUI(data: MovieData) = when (data) {
        is LastSearchData -> setLastSearchData(data.search)
        is MovieListData -> adapter.setItems(data.list)
        is LastSearchError -> listFrame.showErrorInfo(data.error)
        is MovieDataError -> listFrame.showErrorAction(data.error) { viewModel.reloadMovies() }
        else -> Timber.d("Unhandled data %s", data.javaClass.simpleName)
    }

    private fun setLastSearchData(search: Search) {
        if (search.query == null || search.query.isEmpty()) return
        floatingSearchView.setSearchText(search.query)
        search.movies?.takeIf { it.isNotEmpty() }?.let { adapter.setItems(it) }
    }

    private fun showMovieInfo(imdbID: String) {
        val direction = MovieListFragmentDirections.actionOpenInfo(imdbID)
        findNavController().navigate(direction)
    }

}