package com.muratozturk.mova.common.interceptor

import com.muratozturk.mova.domain.source.DataSource
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

class LanguageInterceptor(private val preferenceDataSource: DataSource.Preference) :
    Interceptor {
    override fun intercept(chain: Chain): Response {
        val request = chain.request()
        val languageCode = preferenceDataSource.getCurrentLanguageCode()
        val url =
            request.url.newBuilder().addQueryParameter(LANGUAGE, languageCode).build()
        val newRequest = request.newBuilder().url(url).build()

        return chain.proceed(newRequest)
    }

    companion object {
        private const val LANGUAGE = "language"
    }
}
