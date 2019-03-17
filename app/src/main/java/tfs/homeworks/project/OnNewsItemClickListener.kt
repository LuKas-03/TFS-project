package tfs.homeworks.project

interface OnNewsItemClickListener {
    fun onNewsItemClick(position: Int, news: News, newsType: Int)
}