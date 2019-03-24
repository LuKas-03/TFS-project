package tfs.homeworks.project.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import tfs.homeworks.project.News

@Dao
interface NewsDao {

    @Query("SELECT * FROM news WHERE id=:idToSelect")
    fun getNewsById(idToSelect: Int): News

    @Query("SELECT * FROM news")
    fun getNews():  Array<News>

    @Query("SELECT * FROM news WHERE news.id IN (SELECT id FROM likednews)")
    fun getLikedNews():  Array<News>

    @Insert(onConflict = REPLACE)
    fun insert(news: News)

    @Insert(onConflict = REPLACE)
    fun insert(news: List<News>)

    @Delete
    fun delete(news: News)

    @Query("DELETE FROM news")
    fun deleteAll()
}