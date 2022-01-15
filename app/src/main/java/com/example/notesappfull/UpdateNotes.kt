package com.example.notesappfull

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class UpdateNotes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_notes)

        val pk = intent.getIntExtra("pk", 0)
        val note = intent.getStringExtra("note")

        val database = DbHelper(applicationContext, MainActivity())

        val btn = findViewById<Button>(R.id.btn_update)
        val et = findViewById<EditText>(R.id.et_update_note)
        et.hint = note

        btn.setOnClickListener {
            if(et.text.isNotEmpty())
                database.noteUpdate(pk, et.text.toString())
            Toast.makeText(this, "Update successfully", Toast.LENGTH_LONG).show()

        }

    }
}