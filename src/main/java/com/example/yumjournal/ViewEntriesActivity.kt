package com.example.yumjournal

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yumjournal.adapters.EntryAdapter
import com.example.yumjournal.models.Entry
import com.google.firebase.database.*

class ViewEntriesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EntryAdapter
    private val entries = mutableListOf<Entry>()
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_entries)

        // Exit button
        val btnExit = findViewById<ImageView>(R.id.btnBack)
        btnExit.setOnClickListener { finish() }

        recyclerView = findViewById(R.id.rvEntries)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = EntryAdapter(this, entries)
        recyclerView.adapter = adapter

        dbRef = FirebaseDatabase.getInstance().getReference("entries")
        loadEntriesFromFirebase()
    }

    private fun loadEntriesFromFirebase() {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                entries.clear()
                for (snap in snapshot.children) {
                    val entry = snap.getValue(Entry::class.java)
                    entry?.let {
                        if (it.id.isNotEmpty()) entries.add(it)
                    }
                }
                adapter.notifyDataSetChanged()

                if (entries.isEmpty()) {
                    Toast.makeText(this@ViewEntriesActivity, "No entries found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ViewEntriesActivity, "Failed to load entries: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
