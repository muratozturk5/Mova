package com.muratozturk.metflix.data.source.local

import com.muratozturk.metflix.data.model.local.Bookmark
import com.muratozturk.metflix.data.model.local.Download
import com.muratozturk.metflix.domain.source.DataSource
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val metflixDao: MetflixDao) :
    DataSource.Local {
    override suspend fun addBookmark(bookmark: Bookmark) = metflixDao.addBookmark(bookmark)

    override suspend fun removeBookmark(id: Int) = metflixDao.deleteBookmark(id)

    override suspend fun isBookmarked(id: Int): Boolean = metflixDao.isBookmarked(id)

    override suspend fun getBookmarks(): List<Bookmark> = metflixDao.getBookmarks()
    override suspend fun addDownload(download: Download) = metflixDao.addDownload(download)

    override suspend fun removeDownloaded(id: Int) = metflixDao.deleteDownload(id)

    override suspend fun isDownloaded(id: Int): Boolean = metflixDao.isDownloaded(id)

    override suspend fun getDownloads(): List<Download> = metflixDao.getDownloads()
}