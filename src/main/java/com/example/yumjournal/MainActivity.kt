package com.example.yumjournal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        // Drawer menu
        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_add_food -> startActivity(Intent(this, AddEntryActivity::class.java))
                R.id.nav_view_food -> startActivity(Intent(this, ViewEntriesActivity::class.java))
                R.id.nav_food_recommendation -> startActivity(Intent(this, FoodRecommendationActivity::class.java))
            }
            drawerLayout.closeDrawers()
            true
        }

        // Open drawer
        findViewById<ImageView>(R.id.btnMenu)?.setOnClickListener {
            if (drawerLayout.isDrawerOpen(navView)) drawerLayout.closeDrawer(navView)
            else drawerLayout.openDrawer(navView)
        }

        // Logout
        navView.findViewById<LinearLayout>(R.id.footer_layout)?.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Card buttons
        findViewById<Button>(R.id.btnLasagna)?.setOnClickListener { openFoodDetail("Lasagna") }
        findViewById<Button>(R.id.btnPancit)?.setOnClickListener { openFoodDetail("Pancit Chow Mein") }
        findViewById<Button>(R.id.btnLomi)?.setOnClickListener { openFoodDetail("Lomi") }
    }

    // Open recipe details
    private fun openFoodDetail(recipeName: String) {
        val intent = Intent(this, RecipeDetailActivity::class.java)
        intent.putExtra("recipeName", recipeName) // ✔ FIXED
        startActivity(intent)
    }
}
