package com.muratozturk.metflix.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muratozturk.metflix.data.model.local.Bookmark
import com.muratozturk.metflix.data.model.local.Download

@Database(
    entities = [Bookmark::class, Download::class],
    version = 1,
    exportSchema = false
)
abstract class MetflixDatabase : RoomDatabase() {
    abstract fun metflixDao(): MetflixDao
}