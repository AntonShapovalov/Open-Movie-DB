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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.appComponent?.inject(viewModel)
        with(viewModel) {
            progress.observe(viewLifecycleOwner, Observer { progressBar.showOrHide(it) })
            data.observe(viewLifecycleOwner, Observer { updateUI(it) })
            getMovies("terminator") // TODO: debug
        }
    }

    private fun updateUI(data: MovieData) = when (data) {
        is MovieListData -> adapter.setItems(data.list)
        is MovieErrorData -> listFrame.showError(data.throwable) { viewModel.reloadMovies() }
        else -> Timber.d("Unhandled data %s", data.javaClass.simpleName)
    }

    private fun showMovieInfo(imdbID: String) {
        val direction = MovieListFragmentDirections.actionOpenInfo(imdbID)
        findNavController().navigate(direction)
    }

}