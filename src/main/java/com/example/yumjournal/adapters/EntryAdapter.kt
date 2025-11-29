package com.example.yumjournal.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.yumjournal.AddEntryActivity
import com.example.yumjournal.R
import com.example.yumjournal.models.Entry
import com.google.firebase.database.FirebaseDatabase

class EntryAdapter(
    private val context: Context,
    private val entries: MutableList<Entry>
) : RecyclerView.Adapter<EntryAdapter.EntryViewHolder>() {

    inner class EntryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val btnEdit: Button = view.findViewById(R.id.btnEdit)
        val btnDelete: Button = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_entry, parent, false)
        return EntryViewHolder(view)
    }

    override fun getItemCount(): Int = entries.size

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        val entry = entries[position]

        holder.tvTitle.text = entry.title ?: "No Title"
        holder.tvDescription.text = entry.description ?: "No Description"

        // --- EDIT BUTTON ---
        holder.btnEdit.setOnClickListener {
            val intent = Intent(context, AddEntryActivity::class.java)
            intent.putExtra("ENTRY_ID", entry.id)
            intent.putExtra("ENTRY_TITLE", entry.title)
            intent.putExtra("ENTRY_DESC", entry.description)
            context.startActivity(intent)
        }

        // --- DELETE BUTTON ---
        holder.btnDelete.setOnClickListener {
            val currentPos = holder.adapterPosition
            if (currentPos != RecyclerView.NO_POSITION) {
                val entryToDelete = entries[currentPos]
                val entryId = entryToDelete.id

                if (!entryId.isNullOrEmpty()) {
                    FirebaseDatabase.getInstance().getReference("entries")
                        .child(entryId)
                        .removeValue()
                        .addOnSuccessListener {
                            // Remove only this entry locally
                            entries.removeAt(currentPos)
                            notifyItemRemoved(currentPos)
                            notifyItemRangeChanged(currentPos, entries.size)
                            Toast.makeText(context, "Entry deleted", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Failed to delete entry", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(context, "Invalid entry ID", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
