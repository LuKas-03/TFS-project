package tfs.homeworks.project

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class NewsAdapter(
    private val newsItems : Array<NewsItem>,
    private val onClickListener: OnNewsItemClickListener
) : RecyclerView.Adapter<NewsViewHolder>() {

    override fun onCreateViewHolder(parrent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parrent.context)
        val view = inflater.inflate(R.layout.news_item, parrent, false)
        val holder = NewsViewHolder(view)
        view.setOnClickListener {
            val pos = holder.adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                onClickListener.onNewsItemClick(pos, newsItems[pos], 0)
            }
        }
        return holder
    }

    override fun getItemCount(): Int {
        return newsItems.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = newsItems[position]
        holder.newsTitle.text = newsItem.title
        holder.shortDescription.text = newsItem.shortDescription
        holder.publicationDate.text = newsItem.getDateInLongFormat()
    }
}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var newsTitle: TextView = itemView.findViewById(R.id.newsTitle)
    var shortDescription: TextView = itemView.findViewById(R.id.shortDescription)
    var publicationDate: TextView = itemView.findViewById(R.id.itemDate)
}