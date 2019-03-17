package tfs.homeworks.project

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView


class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var newsTitle: TextView = itemView.findViewById(R.id.newsTitle)
    var shortDescription: TextView = itemView.findViewById(R.id.shortDescription)
    var publicationDate: TextView = itemView.findViewById(R.id.itemDate)
}
