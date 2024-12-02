package com.knu.search.entity

data class SearchRankEntity(
    val keyword: String,
    val score: Double,
    val rank: Int,
    val state: String,
)
