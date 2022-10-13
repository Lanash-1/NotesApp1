package com.example.notesapp.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NotesDBHelper(
    context: Context?,
) : SQLiteOpenHelper(context, "NOTES.DB", null, 1) {


    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE NOTES(ID INTEGER PRIMARY KEY, TITLE TEXT NOT NULL, NOTE TEXT NOT NULL, COLOR TEXT NOT NULL)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}