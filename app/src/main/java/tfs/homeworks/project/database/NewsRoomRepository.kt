package tfs.homeworks.project.database

import android.arch.persistence.room.Room
import android.content.Context
import tfs.homeworks.project.News

class NewsRoomRepository private constructor(context: Context) : Repository{

    private val db: NewsDatabase = Room.databaseBuilder(
        context,
        NewsDatabase::class.java,
        "news")
        .allowMainThreadQueries() // ToDo: убрать после вынесения в отдельный поток
        .build()
    private val newsDao = db.newsDao()
    private val likedNewsDao = db.likedNewsDao()

    override fun getNews(): Array<News> {
        return newsDao.getNews()
    }

    override fun getLikedNews(): Array<News> {
        return newsDao.getLikedNews()
    }

    override fun getNewsById(id: Int): News {
        return newsDao.getNewsById(id)
    }

    override fun insertNews(news: News) {
        newsDao.insert(news)
    }

    override fun addToLikedNews(news: News) {
        likedNewsDao.insertNews(LikedNews(news.id))
    }

    override fun insertNews(news: List<News>) {
        newsDao.insert(news)
    }

    override fun deleteNews(news: News) {
        newsDao.delete(news)
    }

    override fun deleteFromLikedNews(news: News) {
        likedNewsDao.deleteNews(news.id)
    }

    override fun isLikedNews(news: News): Boolean {
        val likedNews = likedNewsDao.getNewsById(news.id)
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