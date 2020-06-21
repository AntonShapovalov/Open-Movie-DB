package concept.omdb.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import concept.omdb.R
import concept.omdb.data.dao.MovieInfo
import concept.omdb.ui.activity.*
import kotlinx.android.synthetic.main.fragment_info.*
import timber.log.Timber

class MovieInfoFragment : Fragment() {

    private val viewModel by viewModels<MovieInfoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(viewModel) {
            activity?.appComponent?.inject(this)
            progress.observe(viewLifecycleOwner, Observer { progressBar.showOrHide(it) })
            data.observe(viewLifecycleOwner, Observer { updateUI(it) })
            arguments?.let { getMovieInfo(MovieInfoFragmentArgs.fromBundle(it).imdbID) }
        }
    }

    private fun updateUI(data: MovieData) = when (data) {
        is MovieInfoData -> setMovieInfo(data.info)
        is MovieDataError -> infoFrame.showErrorAction(data.error) { viewModel.reloadMovieInfo() }
        else -> Timber.d("Unhandled data %s", data.javaClass.simpleName)
    }

    private fun setMovieInfo(info: MovieInfo) {
        Glide.with(this)
            .load(info.poster)
            .placeholder(R.drawable.ic_movie_placeholder)
            .into(imageViewPoster)
        textViewTitle.text = info.title
        textViewYear.text = info.year
        textViewGenre.text = info.genre
        textViewDirector.text = info.director
        textViewActors.text = info.actors
        textViewMetaScore.text = info.metaScore
        textViewImdbRating.text = info.imdbRating
        textViewPlot.text = info.plot
    }

}