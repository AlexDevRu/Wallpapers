package com.example.kulakov_p3_wallpapers_app.test

import com.example.domain.preferences.IPersistantStorage

class PersistantStorageTest: IPersistantStorage {
    val data = mutableMapOf<String, Any?>()

    companion object {
        private const val COLUMN_COUNT = "COLUMN_COUNT"
        private const val QUERY = "QUERY"

        private const val COLUMN_COUNT_DEFAULT = 3
    }

    override fun saveColumnCount(value: Int) {
        data[COLUMN_COUNT] = value
    }

    override fun getColumnCount(): Int {
        return if(data.containsKey(COLUMN_COUNT)) data[COLUMN_COUNT] as Int else COLUMN_COUNT_DEFAULT
    }

    override fun saveQuery(value: String?) {
        data[QUERY] = value
    }

    override fun getQuery(): String? {
        return if(data.containsKey(QUERY)) data[QUERY] as String? else null
    }
}