package com.example.yumjournal

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.yumjournal.models.Entry
import com.google.firebase.database.FirebaseDatabase

class EditEntryActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var descEditText: EditText
    private lateinit var updateBtn: Button
    private lateinit var entryId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entry)

        titleEditText = findViewById(R.id.etEntryTitle)
        descEditText = findViewById(R.id.etEntryDescription)
        updateBtn = findViewById(R.id.btnSaveEntry)

        updateBtn.text = "Update Entry"

        // Get data
        entryId = intent.getStringExtra("id") ?: ""
        titleEditText.setText(intent.getStringExtra("title"))
        descEditText.setText(intent.getStringExtra("description"))

        updateBtn.setOnClickListener { updateEntry() }
    }

    private fun updateEntry() {
        val title = titleEditText.text.toString()
        val desc = descEditText.text.toString()

        val entryRef = FirebaseDatabase.getInstance().getReference("entries").child(entryId)
        val updatedEntry = Entry(entryId, title, desc)

        entryRef.setValue(updatedEntry)
            .addOnSuccessListener {
                Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show()
                finish()
            }
    }
}
