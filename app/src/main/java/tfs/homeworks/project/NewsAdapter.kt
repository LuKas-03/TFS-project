package tfs.homeworks.project

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class NewsAdapter(
    news : List<News>,
    onClickListener: OnNewsItemClickListener
) : RecyclerView.Adapter<NewsViewHolder>() {

    private val newsList = news
    private val listener = onClickListener

    override fun onCreateViewHolder(parrent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parrent.context)
        val view = inflater.inflate(R.layout.news_item, parrent, false)
        val holder = NewsViewHolder(view)
        view.setOnClickListener {
            val pos = holder.adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                listener.onNewsItemClick(pos, newsList[pos], 0)
            }
        }
        return holder
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsList[position]
        holder.newsTitle.text = news.title
        holder.shortDescription.text = news.shortDescription
        if (news.date != null) {
            holder.publicationDate.text = PublicationDateBuildUtil.getPublicationDate(news.date!!)
        }
    }

}