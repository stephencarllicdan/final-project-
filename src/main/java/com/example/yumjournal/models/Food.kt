package com.example.yumjournal.models

data class Food(
    val name: String,
    val category: String,
    val ingredientstext: String,
    val imageResId: Int,
    val source: String = "",
    val serving: Int = 1,
    val prepTime: String = "",
    val cookTime: String = "",
    val ingredients: List<String> = listOf()
)
