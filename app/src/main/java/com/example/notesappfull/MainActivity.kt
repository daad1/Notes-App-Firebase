package com.example.notesappfull

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappfull.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity: AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var recyclerView: RecyclerView
    lateinit var noteList: List<Note>

    private val dbHelper by lazy { NoteDatabase.getDatabase(this).noteDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        addNote()
        getAllNote()
    }


    private fun getAllNote() {
        CoroutineScope(Dispatchers.IO).launch {
            val data = async { dbHelper.getAllNote() }.await()
            if (data.isNotEmpty()) {
                withContext(Dispatchers.Main) {
                    noteList = data
                    recyclerView.adapter = RVAdapter(noteList as ArrayList<Note>, this@MainActivity)
                    recyclerView.adapter!!.notifyDataSetChanged()
                }
            }
        }
    }

    private fun addNote() {
        binding.addButton.setOnClickListener {
            val note = binding.etNote.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                dbHelper.addNote(Note(0, note))
                getAllNote()
            }
            Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show()
        }
    }

    fun raiseDialog(pk: Int) {
        val dialogBuilder = AlertDialog.Builder(this)
        val updatedNote = EditText(this)
        updatedNote.hint = "Update your note"
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Save", DialogInterface.OnClickListener { _, _ ->
                editNote(pk, updatedNote.text.toString())
                recyclerView.adapter!!.notifyDataSetChanged()
                getAllNote()
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.setView(updatedNote)
        alert.show()
    }

    private fun editNote(pk: Int, note: String) {
        CoroutineScope(Dispatchers.IO).launch {
            dbHelper.updateNote(Note(pk, note))
            getAllNote()
        }
        Toast.makeText(this, "Update Successfully", Toast.LENGTH_SHORT).show()
    }

    fun deleteNote(pk: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            dbHelper.deleteNote(Note(pk, ""))
            getAllNote()
        }
        Toast.makeText(this, "Delete Successfully", Toast.LENGTH_SHORT).show()
    }
}