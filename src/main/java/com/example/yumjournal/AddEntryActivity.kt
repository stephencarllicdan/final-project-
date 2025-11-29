package com.example.yumjournal

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.yumjournal.models.Entry
import com.google.firebase.database.FirebaseDatabase

class AddEntryActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var descEditText: EditText
    private lateinit var saveBtn: Button
    private var entryId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entry)

        // Bind top bar exit button
        val btnExit = findViewById<ImageView>(R.id.btnExit)
        btnExit.setOnClickListener {
            finish() // Close activity
        }

        // Bind views
        titleEditText = findViewById(R.id.etEntryTitle)
        descEditText = findViewById(R.id.etEntryDescription)
        saveBtn = findViewById(R.id.btnSaveEntry)

        // Check if editing existing entry
        entryId = intent.getStringExtra("ENTRY_ID")
        val entryTitle = intent.getStringExtra("ENTRY_TITLE")
        val entryDesc = intent.getStringExtra("ENTRY_DESC")

        if (!entryId.isNullOrEmpty()) {
            titleEditText.setText(entryTitle)
            descEditText.setText(entryDesc)
        }

        saveBtn.setOnClickListener {
            val title = titleEditText.text.toString().trim()
            val description = descEditText.text.toString().trim()

            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            saveEntryToFirebase(title, description)
        }
    }

    private fun saveEntryToFirebase(title: String, description: String) {
        val database = FirebaseDatabase.getInstance().getReference("entries")
        val id = entryId ?: database.push().key

        if (id == null) {
            Toast.makeText(this, "Failed to generate entry ID", Toast.LENGTH_SHORT).show()
            return
        }

        val entry = Entry(id, title, description)

        database.child(id).setValue(entry)
            .addOnSuccessListener {
                Toast.makeText(this, "Entry saved!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to save entry: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
