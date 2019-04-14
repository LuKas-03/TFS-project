package tfs.homeworks.project.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LikedNews (
    @PrimaryKey
    var id: Int
)