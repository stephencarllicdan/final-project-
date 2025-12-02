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
import com.example.yumjournal.EditEntryActivity
import com.example.yumjournal.R
import com.example.yumjournal.models.Entry
import com.google.firebase.database.FirebaseDatabase

class EntryAdapter(
    private val context: Context,
    private val entries: MutableList<Entry>
) : RecyclerView.Adapter<EntryAdapter.EntryViewHolder>() {

    inner class EntryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvSource: TextView = view.findViewById(R.id.tvSource)
        val tvServing: TextView = view.findViewById(R.id.tvServing)
        val tvPrepCook: TextView = view.findViewById(R.id.tvPrepCook)
        val tvIngredients: TextView = view.findViewById(R.id.tvIngredients)
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

        holder.tvTitle.text = if (entry.title.isEmpty()) context.getString(R.string.no_title) else entry.title
        holder.tvSource.text = if (entry.source.isEmpty()) context.getString(R.string.source_text, "Recipe Keeper") else context.getString(R.string.source_text, entry.source)
        holder.tvServing.text = context.getString(R.string.serving_text, if (entry.servingSize.isEmpty()) "-" else entry.servingSize)
        holder.tvPrepCook.text = context.getString(R.string.prep_cook_text, if (entry.prepTime.isEmpty()) "-" else entry.prepTime, if (entry.cookTime.isEmpty()) "-" else entry.cookTime)
        holder.tvIngredients.text = if (entry.ingredients.isEmpty()) context.getString(R.string.no_ingredients) else entry.ingredients

        // --- EDIT BUTTON ---
        holder.btnEdit.setOnClickListener {
            if (entry.id.isEmpty()) {
                Toast.makeText(context, context.getString(R.string.invalid_entry_id), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(context, EditEntryActivity::class.java).apply {
                putExtra("ENTRY_ID", entry.id)
                putExtra("ENTRY_TITLE", entry.title)
                putExtra("ENTRY_SOURCE", entry.source)
                putExtra("ENTRY_SERVING", entry.servingSize)
                putExtra("ENTRY_PREP", entry.prepTime)
                putExtra("ENTRY_COOK", entry.cookTime)
                putExtra("ENTRY_INGREDIENTS", entry.ingredients)
            }
            context.startActivity(intent)
        }

        // --- DELETE BUTTON ---
        holder.btnDelete.setOnClickListener {
            val currentPos = holder.bindingAdapterPosition
            if (currentPos != RecyclerView.NO_POSITION) {
                val entryId = entries[currentPos].id
                if (entryId.isNotEmpty()) {
                    FirebaseDatabase.getInstance().getReference("entries")
                        .child(entryId)
                        .removeValue()
                        .addOnSuccessListener {
                            entries.removeAt(currentPos)
                            notifyItemRemoved(currentPos)
                            Toast.makeText(context, context.getString(R.string.entry_deleted), Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, context.getString(R.string.delete_failed), Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(context, context.getString(R.string.invalid_entry_id), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
