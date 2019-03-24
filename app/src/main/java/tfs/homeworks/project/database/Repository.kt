package tfs.homeworks.project.database

import tfs.homeworks.project.News

interface Repository {
    fun getNews(): Array<News>
    fun getLikedNews(): Array<News>
    fun getNewsById(id: Int): News

    fun insertNews(news: News)
    fun addToLikedNews(news: News)
    fun insertNews(news: List<News>)

    fun deleteNews(news: News)
    fun deleteFromLikedNews(news: News)

    fun isLikedNews(news: News): Boolean

    fun deleteAll()
}