package tfs.homeworks.project.database

import android.content.Context
import androidx.room.Room
import io.reactivex.*
import tfs.homeworks.project.NewsItem

class NewsRoomRepository private constructor(context: Context) : Repository{

    private val db: NewsDatabase = Room.databaseBuilder(
        context.applicationContext,
        NewsDatabase::class.java,
        "news")
        .build()
    private val newsDao = db.newsDao()
    private val likedNewsDao = db.likedNewsDao()

    override fun getNews(): Flowable<List<NewsItem>> {
        return newsDao.getNews()
    }

    override fun getLikedNews(): Flowable<List<NewsItem>> {
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

    override fun updateNews(newsItem: NewsItem): Completable {
        return newsDao.update(newsItem)
    }

    override fun deleteNews(newsItem: NewsItem): Completable {
        return newsDao.delete(newsItem)
    }

    override fun deleteFromLikedNews(newsItem: NewsItem): Completable {
        return likedNewsDao.deleteNews(newsItem.id)
    }

    override fun deleteNews(newsItems: List<NewsItem>): Completable {
        return newsDao.delete(newsItems)
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