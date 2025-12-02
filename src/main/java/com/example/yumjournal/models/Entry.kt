package com.example.yumjournal.models

data class Entry(
    val id: String = "",
    val title: String = "",
    val source: String = "",
    val servingSize: String = "",
    val prepTime: String = "",
    val cookTime: String = "",
    val ingredients: String = ""
)
