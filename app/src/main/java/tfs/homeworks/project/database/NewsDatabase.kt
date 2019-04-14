package tfs.homeworks.project.database

import androidx.room.Database
import androidx.room.RoomDatabase
import tfs.homeworks.project.NewsItem

@Database(entities = arrayOf(NewsItem::class, LikedNews::class), version = 1)
abstract class NewsDatabase : RoomDatabase() {
    internal abstract fun newsDao(): NewsDao
    internal abstract fun likedNewsDao(): LikedNewsDao
}