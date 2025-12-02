package com.example.yumjournal

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.yumjournal.models.Entry
import com.google.firebase.database.FirebaseDatabase

class EditEntryActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var sourceEditText: EditText
    private lateinit var servingEditText: EditText
    private lateinit var prepTimeEditText: EditText
    private lateinit var cookTimeEditText: EditText
    private lateinit var ingredientsEditText: EditText
    private lateinit var updateBtn: Button
    private var entryId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entry) // Use AddEntry layout

        // Bind views
        titleEditText = findViewById(R.id.etRecipeTitle)
        sourceEditText = findViewById(R.id.etSource)
        servingEditText = findViewById(R.id.etServingSize)
        prepTimeEditText = findViewById(R.id.etPrepTime)
        cookTimeEditText = findViewById(R.id.etCookTime)
        ingredientsEditText = findViewById(R.id.etIngredients)
        updateBtn = findViewById(R.id.btnSaveRecipe)

        updateBtn.text = "Update Recipe"

        // Back button
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener { finish() }

        // Get data from intent
        entryId = intent.getStringExtra("ENTRY_ID")
        if (entryId.isNullOrEmpty()) {
            Toast.makeText(this, "Invalid entry ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        titleEditText.setText(intent.getStringExtra("ENTRY_TITLE"))
        sourceEditText.setText(intent.getStringExtra("ENTRY_SOURCE"))
        servingEditText.setText(intent.getStringExtra("ENTRY_SERVING"))
        prepTimeEditText.setText(intent.getStringExtra("ENTRY_PREP"))
        cookTimeEditText.setText(intent.getStringExtra("ENTRY_COOK"))
        ingredientsEditText.setText(intent.getStringExtra("ENTRY_INGREDIENTS"))

        updateBtn.setOnClickListener { updateEntry() }
    }

    private fun updateEntry() {
        val title = titleEditText.text.toString().trim()
        val source = sourceEditText.text.toString().trim()
        val serving = servingEditText.text.toString().trim()
        val prepTime = prepTimeEditText.text.toString().trim()
        val cookTime = cookTimeEditText.text.toString().trim()
        val ingredients = ingredientsEditText.text.toString().trim()

        if (title.isEmpty() || ingredients.isEmpty()) {
            Toast.makeText(this, "Please fill Recipe Title and Ingredients", Toast.LENGTH_SHORT).show()
            return
        }

        val id = entryId ?: return
        val updatedEntry = Entry(
            id = id,
            title = title,
            source = source,
            servingSize = serving,
            prepTime = prepTime,
            cookTime = cookTime,
            ingredients = ingredients
        )

        FirebaseDatabase.getInstance().getReference("entries")
            .child(id)
            .setValue(updatedEntry)
            .addOnSuccessListener {
                Toast.makeText(this, "Recipe updated!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to update recipe: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
