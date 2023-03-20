package com.muratozturk.mova.data.source.preference

import android.app.Application
import android.content.SharedPreferences
import android.content.res.Configuration
import com.muratozturk.mova.common.Constants
import com.muratozturk.mova.domain.source.DataSource
import java.util.*
import javax.inject.Inject

class PreferenceDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val app: Application
) :
    DataSource.Preference {
    override fun setDarkMode(isDarkMode: Boolean) {
        sharedPreferences.edit().putBoolean(Constants.Preferences.DARK_MODE, isDarkMode).apply()
    }

    override fun getDarkMode(): Boolean {
        var isDarkMode = false

        when (app.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                isDarkMode = true
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                isDarkMode = false
            }
        }
        return sharedPreferences.getBoolean(
            Constants.Preferences.DARK_MODE,
            isDarkMode
        )
    }


    override fun setCurrentLanguage(language: String) {
        sharedPreferences.edit().putString(Constants.Preferences.LANGUAGE_NAME, language).apply()
    }

    override fun getCurrentLanguage(): String =
        sharedPreferences.getString(
            Constants.Preferences.LANGUAGE_NAME,
            Locale.getDefault().displayLanguage
        ) ?: Locale.getDefault().displayLanguage

    override fun setCurrentLanguageCode(language: String) {
        sharedPreferences.edit().putString(Constants.Preferences.LANGUAGE_CODE, language).apply()
    }

    override fun getCurrentLanguageCode(): String =
        sharedPreferences.getString(
            Constants.Preferences.LANGUAGE_CODE,
            Locale.getDefault().language
        ) ?: Locale.getDefault().language

}