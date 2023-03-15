package com.muratozturk.metflix.data.source.preference

import android.content.SharedPreferences
import com.muratozturk.metflix.common.Constants
import com.muratozturk.metflix.domain.source.DataSource
import javax.inject.Inject

class PreferenceDataSourceImpl @Inject constructor(private val sharedPreferences: SharedPreferences) :
    DataSource.Preference {
    override fun setDarkMode(isDarkMode: Boolean) {
        sharedPreferences.edit().putBoolean(Constants.DARK_MODE, isDarkMode).apply()
    }

    override fun getDarkMode(): Boolean =
        sharedPreferences.getBoolean(Constants.DARK_MODE, false)

}