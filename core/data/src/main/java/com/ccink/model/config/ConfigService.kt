package com.ccink.model.config

interface ConfigService {

    fun getAppLanguage(): String

    fun saveToken(str: String)

    fun getToken(): String?

    fun saveFirsEntry(isFirst: Boolean)

    fun getFirsEntry(): Boolean

    fun saveId(id: Long)

    fun getId(): Long

}