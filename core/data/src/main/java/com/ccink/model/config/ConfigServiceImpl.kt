package com.ccink.model.config

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.ccink.model.Constants

internal class ConfigServiceImpl(context: Context, configFileName: String) : ConfigService {

    private val mSharedPreferences: SharedPreferences = context.getSharedPreferences(configFileName, Context.MODE_PRIVATE)

    override fun getAppLanguage(): String {
        return when (AppCompatDelegate.getApplicationLocales().toLanguageTags()) {
            "ru" -> Constants.RUSSIAN
            else -> Constants.RUSSIAN
        }
    }

    override fun saveToken(str: String) {
        mSharedPreferences.edit().putString("str", str).apply()
    }

    override fun getToken(): String {
        return mSharedPreferences.getString("str", null) ?: ""
    }

    override fun saveFirsEntry(isFirst: Boolean) {
        mSharedPreferences.edit().putBoolean("firsEntry", isFirst).apply()
    }

    override fun getFirsEntry(): Boolean {
        return mSharedPreferences.getBoolean("firsEntry", false)
    }

    override fun saveId(id: Long) {
        mSharedPreferences.edit().putLong("id", id).apply()
    }

    override fun getId(): Long {
        return mSharedPreferences.getLong("id", 0L)
    }

}