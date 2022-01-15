package com.example.notesappfull

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class RVAdapter (private val notes: List<NoteData>, private val ctx: MainActivity): RecyclerView.Adapter<RVAdapter.ViewItemHolder>() {
    class ViewItemHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewItemHolder {
        return ViewItemHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewItemHolder, position: Int) {
        val note = notes[position]

        holder.itemView.apply {
            tv_note.text = note.note

            IV_delete.setOnClickListener {
                val builder = AlertDialog.Builder(holder.itemView.context)
                builder.setTitle("Are you sure wou want to delete this note?")
                builder.setPositiveButton("Delete"){_, _ -> ctx.delete(note.pk)}
                builder.setNegativeButton("Cancel"){_, _ ->}

                builder.show()
            }

            IV_edit.setOnClickListener {
                val intent = Intent(holder.itemView.context, UpdateNotes::class.java)
                intent.putExtra("pk", note.pk)
                intent.putExtra("note", note.note)
                holder.itemView.context.startActivity(intent)
            }

        }
    }

    override fun getItemCount(): Int = notes.size
}