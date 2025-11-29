package com.example.yumjournal

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yumjournal.adapters.FoodAdapter
import com.example.yumjournal.models.Food

class FoodRecommendationActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FoodAdapter
    private val foods = mutableListOf<Food>()
    private val displayedFoods = mutableListOf<Food>()
    private lateinit var categoryContainer: LinearLayout
    private var selectedCategory: String = "All"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_recommendation)

        recyclerView = findViewById(R.id.rvFoodRecommendation)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = FoodAdapter(this, displayedFoods)
        recyclerView.adapter = adapter

        categoryContainer = findViewById(R.id.categoryContainer)

        // Setup categories
        val categories = listOf("All", "Filipino", "Japanese", "Italian")
        categories.forEach { category ->
            val tv = TextView(this)
            tv.text = category
            tv.setPadding(40, 20, 40, 20)
            tv.setBackgroundColor(if (category == selectedCategory) Color.DKGRAY else Color.LTGRAY)
            tv.setTextColor(if (category == selectedCategory) Color.WHITE else Color.BLACK)
            tv.setOnClickListener {
                selectCategory(category)
            }
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(12, 0, 12, 0)
            categoryContainer.addView(tv, params)
        }

        loadFoods()
        filterCategory(selectedCategory)

        // Exit button
        val btnExit = findViewById<ImageView>(R.id.btnExit)
        btnExit.setOnClickListener { finish() }
    }

    private fun loadFoods() {
        foods.clear()

        // Filipino dishes
        foods.add(Food("Adobo", "Filipino", "Chicken, Soy Sauce, Vinegar, Garlic", "https://upload.wikimedia.org/wikipedia/commons/6/60/Chicken_adobo.jpg"))
        foods.add(Food("Sinigang", "Filipino", "Pork, Tamarind, Vegetables", "https://upload.wikimedia.org/wikipedia/commons/6/6f/Sinigang_na_baboy.JPG"))
        foods.add(Food("Lechon", "Filipino", "Whole Pig, Salt, Spices", "https://upload.wikimedia.org/wikipedia/commons/7/7f/Lechon_in_Cebu.JPG"))

        // Japanese dishes
        foods.add(Food("Sushi", "Japanese", "Rice, Nori, Raw Fish", "https://upload.wikimedia.org/wikipedia/commons/6/60/Sushi_platter.jpg"))
        foods.add(Food("Ramen", "Japanese", "Noodles, Broth, Pork, Egg", "https://upload.wikimedia.org/wikipedia/commons/6/6e/Japanese_Ramen.jpg"))
        foods.add(Food("Tempura", "Japanese", "Shrimp, Vegetables, Batter", "https://upload.wikimedia.org/wikipedia/commons/6/6e/Tempura_dish.jpg"))

        // Italian dishes
        foods.add(Food("Pasta Carbonara", "Italian", "Pasta, Egg, Bacon, Parmesan Cheese", "https://upload.wikimedia.org/wikipedia/commons/3/3b/Spaghetti_alla_Carbonara.jpg"))
        foods.add(Food("Pizza Margherita", "Italian", "Dough, Tomato, Cheese, Basil", "https://upload.wikimedia.org/wikipedia/commons/d/d3/Pizza_Margherita_stu_spianata.jpg"))
        foods.add(Food("Lasagna", "Italian", "Pasta, Meat, Cheese, Tomato Sauce", "https://upload.wikimedia.org/wikipedia/commons/0/0b/Lasagna_al_forno.jpg"))
    }

    private fun selectCategory(category: String) {
        selectedCategory = category
        updateCategoryUI()
        filterCategory(category)
    }

    private fun updateCategoryUI() {
        for (i in 0 until categoryContainer.childCount) {
            val tv = categoryContainer.getChildAt(i) as TextView
            if (tv.text == selectedCategory) {
                tv.setBackgroundColor(Color.DKGRAY)
                tv.setTextColor(Color.WHITE)
            } else {
                tv.setBackgroundColor(Color.LTGRAY)
                tv.setTextColor(Color.BLACK)
            }
        }
    }

    private fun filterCategory(category: String) {
        displayedFoods.clear()
        if (category == "All") {
            displayedFoods.addAll(foods)
        } else {
            displayedFoods.addAll(foods.filter { it.category == category })
        }
        adapter.notifyDataSetChanged()
    }
}
