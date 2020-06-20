package concept.omdb.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import concept.omdb.R
import concept.omdb.data.dao.Movie
import kotlinx.android.synthetic.main.list_item.view.*

/**
 * List adapter for [MovieListFragment]
 */
class MovieListAdapter(private val onItemClick: (String) -> Unit) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    private val items: ArrayList<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        val holder = ViewHolder(v)
        v.setOnClickListener {
            val position = holder.adapterPosition
            if (position >= 0) onItemClick(items[position].imdbID)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val view = holder.itemView
        view.textViewTitle.text = item.title
        view.textViewYear.text = item.year
        view.textViewType.text = item.type
        Glide.with(view.context)
            .load(item.poster)
            .placeholder(R.drawable.ic_movie_placeholder)
            .into(view.imageViewPoster)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: List<Movie>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}