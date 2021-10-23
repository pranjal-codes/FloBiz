package com.example.flobiz.models

data class QuestionFeed(
    val has_more: Boolean,
    val items: List<Item>,
    val quota_max: Int,
    val quota_remaining: Int
)