package tfs.homeworks.project

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class NewsAdapter(
    private val dataset : Array<Any>,
    private val onClickListener: OnNewsItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parrent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parrent.context)
        when(viewType) {
            TYPE_NEWS -> {
                val view = inflater.inflate(R.layout.news_item, parrent, false)
                val holder = NewsViewHolder(view)
                view.setOnClickListener {
                    val pos = holder.adapterPosition
                    if (pos != RecyclerView.NO_POSITION) {
                        onClickListener.onNewsItemClick(pos, dataset[pos] as NewsItem, 0)
                    }
                }
                return holder
            }
            TYPE_HEADER -> {
                val view = inflater.inflate(R.layout.news_group_header, parrent, false)
                return NewsGroupViewHolder(view)
            }
            else -> throw IllegalArgumentException("unknown viewType=$viewType")
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        when (viewType) {
            TYPE_NEWS -> {
                val newsItem = dataset[position] as NewsItem
                val viewHolder = holder as NewsViewHolder
                viewHolder.newsTitle.text = newsItem.title
                viewHolder.shortDescription.text = newsItem.shortDescription
            }
            TYPE_HEADER -> {
                val newsItem = dataset[position] as NewsGroupHeader
                val viewHolder = holder as NewsGroupViewHolder
                viewHolder.header.text = newsItem.dateFromLongFormat
            }
            else -> throw IllegalArgumentException("unknown viewType=$viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataset[position] is NewsItem) TYPE_NEWS else TYPE_HEADER
    }

    companion object {
        private const val TYPE_NEWS = 0
        private const val TYPE_HEADER = 1
    }
}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var newsTitle: TextView = itemView.findViewById(R.id.newsTitle)
    var shortDescription: TextView = itemView.findViewById(R.id.shortDescription)
}

class NewsGroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var header: TextView = itemView.findViewById(R.id.groupHeader)
}