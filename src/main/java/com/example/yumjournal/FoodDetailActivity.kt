package com.example.yumjournal

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.yumjournal.models.Food

class FoodDetailActivity : AppCompatActivity() {

    private lateinit var ivFood: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvCategory: TextView
    private lateinit var tvSource: TextView
    private lateinit var tvServing: TextView
    private lateinit var tvPrepTime: TextView
    private lateinit var tvCookTime: TextView
    private lateinit var tvIngredients: TextView

    private val foods = listOf(
        // Filipino
        Food("Adobo","Filipino","Chicken, Soy Sauce, Vinegar, Garlic",R.drawable.adobo,"Recipe Keeper",4,"15 mins","45 mins",
            listOf("500g chicken","1/4 cup soy sauce","1/4 cup vinegar","3 cloves garlic")),
        Food("Sinigang","Filipino","Pork, Tamarind, Vegetables",R.drawable.sinigang,"Recipe Keeper",6,"20 mins","1 hour",
            listOf("500g pork","1 pack tamarind mix","Vegetables")),
        Food("Lechon","Filipino","Whole Pig, Salt, Spices",R.drawable.lechon,"Recipe Keeper",10,"30 mins","4 hours",
            listOf("1 whole pig","Salt","Spices")),

        // Japanese
        Food("Sushi","Japanese","Rice, Nori, Raw Fish",R.drawable.sushi,"Recipe Keeper",2,"30 mins","0 mins",
            listOf("1 cup rice","Nori sheets","Raw fish")),
        Food("Ramen","Japanese","Noodles, Broth, Pork, Egg",R.drawable.ramen,"Recipe Keeper",2,"20 mins","40 mins",
            listOf("200g noodles","Broth","Pork slices","1 egg")),
        Food("Tempura","Japanese","Shrimp, Vegetables, Batter",R.drawable.tempura,"Recipe Keeper",4,"15 mins","20 mins",
            listOf("Shrimp","Vegetables","Batter mix")),

        // Korean
        Food("Bibimbap","Korean","Rice, Vegetables, Beef, Egg",R.drawable.bibimbap,"Recipe Keeper",2,"20 mins","10 mins",
            listOf("Rice","Vegetables","Beef","1 egg")),
        Food("Kimchi Jjigae","Korean","Kimchi, Pork, Tofu, Vegetables",R.drawable.kimchi_jjigae,"Recipe Keeper",4,"15 mins","30 mins",
            listOf("Kimchi","Pork","Tofu","Vegetables")),
        Food("Bulgogi","Korean","Beef, Soy Sauce, Garlic, Sugar",R.drawable.bulgogi,"Recipe Keeper",4,"20 mins","15 mins",
            listOf("Beef","Soy sauce","Garlic","Sugar"))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        ivFood = findViewById(R.id.ivFood)
        tvTitle = findViewById(R.id.tvTitle)
        tvCategory = findViewById(R.id.tvCategory)
        tvSource = findViewById(R.id.tvSource)
        tvServing = findViewById(R.id.tvServing)
        tvPrepTime = findViewById(R.id.tvPrepTime)
        tvCookTime = findViewById(R.id.tvCookTime)
        tvIngredients = findViewById(R.id.tvIngredients)

        val foodName = intent.getStringExtra("foodName") ?: return
        val food = foods.find { it.name == foodName } ?: return

        ivFood.setImageResource(food.imageResId)
        tvTitle.text = food.name
        tvCategory.text = food.category
        tvSource.text = food.source
        tvServing.text = "Serving size: ${food.serving}"
        tvPrepTime.text = "Prep time: ${food.prepTime}"
        tvCookTime.text = "Cook time: ${food.cookTime}"
        tvIngredients.text = food.ingredients.joinToString("\n")
    }
}
