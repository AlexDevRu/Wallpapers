package com.example.data.preferences

import android.content.Context
import android.content.SharedPreferences

class PersistantStorage(context: Context) {
    private val settings: SharedPreferences = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = settings.edit()

    companion object {
        private const val STORAGE_NAME = "Settings"

        private const val COLUMN_COUNT = "COLUMN_COUNT"
        private const val QUERY = "QUERY"

        private const val COLUMN_COUNT_DEFAULT = 3
    }

    fun saveColumnCount(value: Int) {
        editor.putInt(COLUMN_COUNT, value)
        editor.commit()
    }

    fun getColumnCount() = settings.getInt(COLUMN_COUNT, COLUMN_COUNT_DEFAULT)


    fun saveQuery(value: String?) {
        editor.putString(QUERY, value)
        editor.commit()
    }

    fun getQuery() = settings.getString(QUERY, null)
}