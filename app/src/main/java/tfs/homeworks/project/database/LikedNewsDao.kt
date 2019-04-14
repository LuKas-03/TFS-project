package tfs.homeworks.project.database

import android.arch.persistence.room.*
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface LikedNewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNews(newsId: LikedNews): Completable

    @Query("DELETE FROM likednews WHERE id=:newsId")
    fun deleteNews(newsId: Int): Completable

    @Query("SELECT * FROM likednews WHERE id=:newsId")
    fun getNewsById(newsId: Int): Maybe<LikedNews>

    @Query("DELETE FROM likednews")
    fun deleteAll()
}