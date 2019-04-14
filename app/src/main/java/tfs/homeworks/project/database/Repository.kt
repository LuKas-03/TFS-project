package tfs.homeworks.project.database

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import tfs.homeworks.project.NewsItem

interface Repository {
    fun getNews(): Flowable<NewsItem>
    fun getLikedNews(): Flowable<NewsItem>
    fun getNewsById(id: Int): Single<NewsItem>

    fun insertNews(newsItem: NewsItem): Completable
    fun addToLikedNews(newsItem: NewsItem): Completable
    fun insertNews(newsItems: List<NewsItem>): Completable

    fun deleteNews(newsItem: NewsItem)
    fun deleteFromLikedNews(newsItem: NewsItem): Completable

    fun isLikedNews(newsItem: NewsItem): Single<Boolean>

    fun deleteAll()
}