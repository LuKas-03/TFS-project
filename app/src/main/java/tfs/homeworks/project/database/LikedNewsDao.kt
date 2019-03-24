package tfs.homeworks.project.database

import android.arch.persistence.room.*

@Dao
interface LikedNewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNews(newsId: LikedNews)

    @Query("DELETE FROM likednews WHERE id=:newsId")
    fun deleteNews(newsId: Int)

    @Query("SELECT * FROM likednews WHERE id=:newsId")
    fun getNewsById(newsId: Int): LikedNews?

    @Query("DELETE FROM likednews")
    fun deleteAll()
}