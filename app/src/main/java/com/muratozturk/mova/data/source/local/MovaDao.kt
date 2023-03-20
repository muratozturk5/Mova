package com.muratozturk.mova.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muratozturk.mova.common.Constants
import com.muratozturk.mova.data.model.local.Bookmark
import com.muratozturk.mova.data.model.local.Download

@Dao
interface MovaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookmark(bookmark: Bookmark)

    @Query(Constants.Queries.DELETE_BOOKMARK)
    suspend fun deleteBookmark(id: Int)

    @Query(Constants.Queries.IS_BOOKMARKED)
    suspend fun isBookmarked(id: Int): Boolean

    @Query(Constants.Queries.GET_BOOKMARKS)
    suspend fun getBookmarks(): List<Bookmark>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDownload(download: Download)

    @Query(Constants.Queries.DELETE_DOWNLOAD)
    suspend fun deleteDownload(id: Int)

    @Query(Constants.Queries.IS_DOWNLOADED)
    suspend fun isDownloaded(id: Int): Boolean

    @Query(Constants.Queries.GET_DOWNLOADS)
    suspend fun getDownloads(): List<Download>
}