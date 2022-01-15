package com.example.notesappfull

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DbHelper(context: Context, private val activity : MainActivity):SQLiteOpenHelper(context,"notes.db",null,2) {
  private  val sqlite : SQLiteDatabase = writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table Notes(pk INTEGER PRIMARY KEY AUTOINCREMENT , Note text)")


    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS Notes ")
        onCreate(db)
    }
    fun addNotes(Note: String ){
        val contentValues = ContentValues()
        contentValues.put("NoteTitle",Note)

        sqlite.insert("Notes",null , contentValues)


    }
    fun retrieveNotes(): MutableList<NoteData>{
        val res = mutableListOf<NoteData>()
        val cursor : Cursor = sqlite.rawQuery("SELECT * FROM Notes",null)
        if (cursor.moveToFirst()){
            res.add(NoteData(cursor.getInt(0), cursor.getString(1).toString()))
            while (cursor.moveToNext()){
                res.add(NoteData(cursor.getInt(0), cursor.getString(1).toString()))
            }
        }
        return  res
    }

    fun noteDelete(p : Int){
        val res = sqlite.delete("Notes","pk = ?", arrayOf(p.toString()))
        activity.notifyRecycler()
    }

    fun noteUpdate(pk: Int , newNote : String){
        val contentValues =ContentValues()
        contentValues.put("note",newNote)
        val res = sqlite.update("Notes",contentValues,"pk = ?", arrayOf(pk.toString()))
    }

}