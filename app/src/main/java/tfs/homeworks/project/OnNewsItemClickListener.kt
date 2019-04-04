package tfs.homeworks.project

interface OnNewsItemClickListener {
    fun onNewsItemClick(position: Int, newsItem: NewsItem, newsType: Int)
}