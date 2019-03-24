package tfs.homeworks.project.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import tfs.homeworks.project.News

@Database(entities = arrayOf(News::class, LikedNews::class), version = 1)
abstract class NewsDatabase : RoomDatabase() {
    internal abstract fun newsDao(): NewsDao
    internal abstract fun likedNewsDao(): LikedNewsDao
}