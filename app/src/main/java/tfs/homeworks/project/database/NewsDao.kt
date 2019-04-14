package tfs.homeworks.project.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import tfs.homeworks.project.NewsItem

@Dao
interface NewsDao {

    @Query("SELECT * FROM NewsItem WHERE id=:idToSelect")
    fun getNewsById(idToSelect: Int): Single<NewsItem>

    @Query("SELECT * FROM NewsItem")
    fun getNews():  Flowable<NewsItem>

    @Query("SELECT * FROM NewsItem WHERE NewsItem.id IN (SELECT id FROM likednews)")
    fun getLikedNews():  Flowable<NewsItem>

    @Insert(onConflict = REPLACE)
    fun insert(newsItem: NewsItem): Completable

    @Insert(onConflict = REPLACE)
    fun insert(newsItems: List<NewsItem>): Completable

    @Delete
    fun delete(newsItem: NewsItem)

    @Query("DELETE FROM NewsItem")
    fun deleteAll()
}