package com.muratozturk.metflix.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muratozturk.metflix.data.model.local.Bookmark
import com.muratozturk.metflix.data.model.local.Download

@Dao
interface MetflixDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookmark(bookmark: Bookmark)

    @Query("DELETE FROM bookmarks WHERE id = :id")
    suspend fun deleteBookmark(id: Int)

    @Query("SELECT EXISTS(SELECT * FROM bookmarks WHERE id = :id)")
    suspend fun isBookmarked(id: Int): Boolean

    @Query("SELECT * FROM bookmarks ORDER BY addDate DESC")
    suspend fun getBookmarks(): List<Bookmark>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDownload(download: Download)

    @Query("DELETE FROM downloads WHERE id = :id")
    suspend fun deleteDownload(id: Int)

    @Query("SELECT EXISTS(SELECT * FROM downloads WHERE id = :id)")
    suspend fun isDownloaded(id: Int): Boolean

    @Query("SELECT * FROM downloads ORDER BY addDate DESC")
    suspend fun getDownloads(): List<Download>
}