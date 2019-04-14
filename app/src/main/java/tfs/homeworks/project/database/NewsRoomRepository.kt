package tfs.homeworks.project.database

import android.arch.persistence.room.Room
import android.content.Context
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import tfs.homeworks.project.NewsItem

class NewsRoomRepository private constructor(context: Context) : Repository{

    private val db: NewsDatabase = Room.databaseBuilder(
        context,
        NewsDatabase::class.java,
        "news")
        //.allowMainThreadQueries() // ToDo: убрать после вынесения в отдельный поток
        .build()
    private val newsDao = db.newsDao()
    private val likedNewsDao = db.likedNewsDao()

    override fun getNews(): Flowable<NewsItem> {
        return newsDao.getNews()
    }

    override fun getLikedNews(): Flowable<NewsItem> {
        return newsDao.getLikedNews()
    }

    override fun getNewsById(id: Int): Single<NewsItem> {
        return newsDao.getNewsById(id)
    }

    override fun insertNews(newsItem: NewsItem): Completable {
        return newsDao.insert(newsItem)
    }

    override fun addToLikedNews(newsItem: NewsItem): Completable {
        return likedNewsDao.insertNews(LikedNews(newsItem.id))
    }

    override fun insertNews(newsItems: List<NewsItem>): Completable {
        return newsDao.insert(newsItems)
    }

    override fun deleteNews(newsItem: NewsItem) {
        newsDao.delete(newsItem)
    }

    override fun deleteFromLikedNews(newsItem: NewsItem): Completable {
        return likedNewsDao.deleteNews(newsItem.id)
    }

    override fun isLikedNews(newsItem: NewsItem): Single<Boolean> {
        return likedNewsDao.getNewsById(newsItem.id).isEmpty.map { i -> !i }
    }

    override fun deleteAll() {
        likedNewsDao.deleteAll()
        newsDao.deleteAll()
    }

    companion object {
        private var instance: NewsRoomRepository? = null
        fun getInstance(context: Context): NewsRoomRepository {
            if (instance == null) {
                instance = NewsRoomRepository(context)
            }
            return instance!!
        }
    }
}