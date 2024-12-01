package com.knu.search.repository

interface RecentKeyWordRepository {
    fun putStringList(key: String, value: List<String>)
    fun getStringList(key: String): List<String>
}
