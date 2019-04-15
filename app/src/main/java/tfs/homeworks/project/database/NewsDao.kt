package tfs.homeworks.project.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import tfs.homeworks.project.NewsItem

@Dao
interface NewsDao {

    @Query("SELECT * FROM NewsItem WHERE id=:idToSelect")
    fun getNewsById(idToSelect: Int): Single<NewsItem>

    @Query("SELECT * FROM NewsItem")
    fun getNews():  Flowable<List<NewsItem>>

    @Query("SELECT * FROM NewsItem WHERE NewsItem.id IN (SELECT id FROM likednews)")
    fun getLikedNews():  Flowable<List<NewsItem>>

    @Insert(onConflict = REPLACE)
    fun insert(newsItem: NewsItem): Completable

    @Insert(onConflict = REPLACE)
    fun insert(newsItems: List<NewsItem>): Completable

    @Delete
    fun delete(newsItem: NewsItem)

    @Query("DELETE FROM NewsItem")
    fun deleteAll()
}