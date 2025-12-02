package com.example.yumjournal

import android.content.Intent
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

        categoryContainer = findViewById(R.id.categoryContainer)

        // LOAD FOODS
        loadFoods()
        displayedFoods.addAll(foods)

        // ADAPTER
        adapter = FoodAdapter(displayedFoods) { food ->
            val intent = Intent(this, RecipeDetailActivity::class.java)
            intent.putExtra("recipeName", food.name)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        // CATEGORY BUTTONS
        val categories = listOf("All", "Filipino", "Japanese", "Korean")
        categories.forEach { category ->
            val tv = TextView(this)
            tv.text = category
            tv.setPadding(40, 20, 40, 20)

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(12, 0, 12, 0)
            tv.layoutParams = params

            tv.setOnClickListener { selectCategory(category) }
            categoryContainer.addView(tv)
        }
        updateCategoryUI()

        // CHECK IF SELECTED FOOD IS SENT FROM PREVIOUS SCREEN
        intent.getStringExtra("selectedFood")?.let { selectedName ->
            val food = foods.find { it.name == selectedName }
            if (food != null) {
                displayedFoods.clear()
                displayedFoods.add(food)
                adapter.notifyDataSetChanged()
            }
        }

        // FIXES CRASH: SAFETY CHECK IF btnExit EXISTS
        val exitBtn = findViewById<ImageView?>(R.id.btnBack)
        exitBtn?.setOnClickListener { finish() }
    }

    private fun loadFoods() {
        foods.clear()

        // Filipino
        foods.add(Food("Adobo", "Filipino", "Chicken, Soy Sauce, Vinegar, Garlic", R.drawable.adobo))
        foods.add(Food("Sinigang", "Filipino", "Pork, Tamarind, Vegetables", R.drawable.sinigang))
        foods.add(Food("Lechon", "Filipino", "Whole Pig, Salt, Spices", R.drawable.lechon))

        // Japanese
        foods.add(Food("Sushi", "Japanese", "Rice, Nori, Raw Fish", R.drawable.sushi))
        foods.add(Food("Ramen", "Japanese", "Noodles, Broth, Pork, Egg", R.drawable.ramen))
        foods.add(Food("Tempura", "Japanese", "Shrimp, Vegetables, Batter", R.drawable.tempura))

        // Korean
        foods.add(Food("Bibimbap", "Korean", "Rice, Vegetables, Beef, Egg", R.drawable.bibimbap))
        foods.add(Food("Kimchi Jjigae", "Korean", "Kimchi, Pork, Tofu, Vegetables", R.drawable.kimchi_jjigae))
        foods.add(Food("Bulgogi", "Korean", "Beef, Soy Sauce, Garlic, Sugar", R.drawable.bulgogi))
    }

    private fun selectCategory(category: String) {
        selectedCategory = category
        updateCategoryUI()

        displayedFoods.clear()
        if (category == "All") {
            displayedFoods.addAll(foods)
        } else {
            displayedFoods.addAll(foods.filter { it.category == category })
        }

        adapter.notifyDataSetChanged()
    }

    private fun updateCategoryUI() {
        for (i in 0 until categoryContainer.childCount) {
            val tv = categoryContainer.getChildAt(i) as TextView
            if (tv.text == selectedCategory) {
                tv.setBackgroundColor(Color.RED)
                tv.setTextColor(Color.WHITE)
            } else {
                tv.setBackgroundColor(Color.LTGRAY)
                tv.setTextColor(Color.BLACK)
            }
        }
    }
}
