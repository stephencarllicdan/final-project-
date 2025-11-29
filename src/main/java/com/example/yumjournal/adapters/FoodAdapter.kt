package com.example.yumjournal.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yumjournal.R
import com.example.yumjournal.models.Food

class FoodAdapter(
    private val context: Context,
    private val foods: List<Food>
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivImage: ImageView = view.findViewById(R.id.ivFoodImage)
        val tvTitle: TextView = view.findViewById(R.id.tvFoodTitle)
        val tvCategory: TextView = view.findViewById(R.id.tvFoodCategory)
        val tvIngredients: TextView = view.findViewById(R.id.tvFoodIngredients)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun getItemCount(): Int = foods.size

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foods[position]
        holder.tvTitle.text = food.name.ifEmpty { "No name" }
        holder.tvCategory.text = food.category.ifEmpty { "Unknown" }
        holder.tvIngredients.text = food.ingredients.ifEmpty { "No ingredients" }

        Glide.with(context)
            .load(food.imageUrl.ifEmpty { "" })
            .placeholder(R.drawable.foodgood)
            .error(R.drawable.foodgood)
            .into(holder.ivImage)
    }
}
