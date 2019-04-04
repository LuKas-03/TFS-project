package tfs.homeworks.project.database

import android.arch.persistence.room.Room
import android.content.Context
import tfs.homeworks.project.NewsItem

class NewsRoomRepository private constructor(context: Context) : Repository{

    private val db: NewsDatabase = Room.databaseBuilder(
        context,
        NewsDatabase::class.java,
        "news")
        .allowMainThreadQueries() // ToDo: убрать после вынесения в отдельный поток
        .build()
    private val newsDao = db.newsDao()
    private val likedNewsDao = db.likedNewsDao()

    override fun getNews(): Array<NewsItem> {
        return newsDao.getNews()
    }

    override fun getLikedNews(): Array<NewsItem> {
        return newsDao.getLikedNews()
    }

    override fun getNewsById(id: Int): NewsItem {
        return newsDao.getNewsById(id)
    }

    override fun insertNews(newsItem: NewsItem) {
        newsDao.insert(newsItem)
    }

    override fun addToLikedNews(newsItem: NewsItem) {
        likedNewsDao.insertNews(LikedNews(newsItem.id))
    }

    override fun insertNews(newsItems: List<NewsItem>) {
        newsDao.insert(newsItems)
    }

    override fun deleteNews(newsItem: NewsItem) {
        newsDao.delete(newsItem)
    }

    override fun deleteFromLikedNews(newsItem: NewsItem) {
        likedNewsDao.deleteNews(newsItem.id)
    }

    override fun isLikedNews(newsItem: NewsItem): Boolean {
        val likedNews = likedNewsDao.getNewsById(newsItem.id)
        return likedNews != null
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