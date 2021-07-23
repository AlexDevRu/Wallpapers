package com.example.domain.preferences

interface IPersistantStorage {
    fun saveColumnCount(value: Int)
    fun getColumnCount(): Int

    fun saveQuery(value: String?)
    fun getQuery(): String?
}