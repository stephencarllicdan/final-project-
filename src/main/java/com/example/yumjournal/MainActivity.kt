package com.example.yumjournal

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        navView.setNavigationItemSelectedListener(this)

        // Open/Close Drawer
        findViewById<ImageView>(R.id.btnMenu).setOnClickListener {
            if (drawerLayout.isDrawerOpen(navView)) drawerLayout.closeDrawer(navView)
            else drawerLayout.openDrawer(navView)
        }

        // Footer Logout Button
        val footerLayout: LinearLayout = navView.findViewById(R.id.footer_layout)
        footerLayout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_add_food -> startActivity(Intent(this, AddEntryActivity::class.java))
            R.id.nav_view_food -> startActivity(Intent(this, ViewEntriesActivity::class.java))
            R.id.nav_food_recommendation -> startActivity(Intent(this, FoodRecommendationActivity::class.java))
        }
        drawerLayout.closeDrawers()
        return true
    }
}
