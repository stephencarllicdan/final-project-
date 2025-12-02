package com.example.yumjournal.adapters

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
    private val foods: List<Food>,
    private val onItemClick: (Food) -> Unit
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ivImage: ImageView = view.findViewById(R.id.ivFoodImage)
        private val tvTitle: TextView = view.findViewById(R.id.tvFoodTitle)
        private val tvCategory: TextView = view.findViewById(R.id.tvFoodCategory)
        private val tvIngredients: TextView = view.findViewById(R.id.tvFoodIngredients)

        fun bind(food: Food) {
            tvTitle.text = food.name
            tvCategory.text = food.category
            tvIngredients.text = food.ingredientstext

            Glide.with(itemView.context)
                .load(food.imageResId)
                .placeholder(R.drawable.foodgood)
                .error(R.drawable.foodgood)
                .into(ivImage)

            // Click item to open recipe
            itemView.setOnClickListener { onItemClick(food) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun getItemCount(): Int = foods.size

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foods[position])
    }
}
