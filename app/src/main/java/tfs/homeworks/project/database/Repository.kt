package tfs.homeworks.project.database

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import tfs.homeworks.project.NewsItem

interface Repository {
    fun getNews(): Flowable<List<NewsItem>>
    fun getLikedNews(): Flowable<List<NewsItem>>
    fun getNewsById(id: Int): Single<NewsItem>

    fun insertNews(newsItem: NewsItem): Completable
    fun addToLikedNews(newsItem: NewsItem): Completable
    fun insertNews(newsItems: List<NewsItem>): Completable

    fun updateNews(newsItem: NewsItem): Completable

    fun deleteNews(newsItem: NewsItem): Completable
    fun deleteFromLikedNews(newsItem: NewsItem): Completable
    fun deleteNews(newsItems: List<NewsItem>): Completable

    fun isLikedNews(newsItem: NewsItem): Single<Boolean>

    fun deleteAll()
}