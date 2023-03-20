package com.muratozturk.mova.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muratozturk.mova.data.model.local.Bookmark
import com.muratozturk.mova.data.model.local.Download

@Database(
    entities = [Bookmark::class, Download::class],
    version = 1,
    exportSchema = false
)
abstract class MovaDatabase : RoomDatabase() {
    abstract fun metflixDao(): MovaDao
}