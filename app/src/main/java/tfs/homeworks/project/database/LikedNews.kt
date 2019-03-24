package tfs.homeworks.project.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class LikedNews (
    @PrimaryKey
    var id: Int
)