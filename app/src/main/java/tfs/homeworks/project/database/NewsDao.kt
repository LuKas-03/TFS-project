package tfs.homeworks.project.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import tfs.homeworks.project.NewsItem

@Dao
interface NewsDao {

    @Query("SELECT * FROM NewsItem WHERE id=:idToSelect")
    fun getNewsById(idToSelect: Int): NewsItem

    @Query("SELECT * FROM NewsItem")
    fun getNews():  Array<NewsItem>

    @Query("SELECT * FROM NewsItem WHERE NewsItem.id IN (SELECT id FROM likednews)")
    fun getLikedNews():  Array<NewsItem>

    @Insert(onConflict = REPLACE)
    fun insert(newsItem: NewsItem)

    @Insert(onConflict = REPLACE)
    fun insert(newsItems: List<NewsItem>)

    @Delete
    fun delete(newsItem: NewsItem)

    @Query("DELETE FROM NewsItem")
    fun deleteAll()
}