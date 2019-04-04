package tfs.homeworks.project.database

import tfs.homeworks.project.NewsItem

interface Repository {
    fun getNews(): Array<NewsItem>
    fun getLikedNews(): Array<NewsItem>
    fun getNewsById(id: Int): NewsItem

    fun insertNews(newsItem: NewsItem)
    fun addToLikedNews(newsItem: NewsItem)
    fun insertNews(newsItems: List<NewsItem>)

    fun deleteNews(newsItem: NewsItem)
    fun deleteFromLikedNews(newsItem: NewsItem)

    fun isLikedNews(newsItem: NewsItem): Boolean

    fun deleteAll()
}