package com.muratozturk.mova.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.muratozturk.mova.common.enums.MediaTypeEnum
import java.util.*

@Entity(tableName = "bookmarks")
data class Bookmark(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    var imageFilePath: String,
    val poster: String,
    val voteAverage: Double,
    val type: MediaTypeEnum,
    val addDate: String = Calendar.getInstance().time.toString()
)
