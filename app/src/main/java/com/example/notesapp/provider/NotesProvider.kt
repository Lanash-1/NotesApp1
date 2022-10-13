package com.example.notesapp.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri

class NotesProvider: ContentProvider() {


    companion object{
        private const val PROVIDER_NAME = "com.example.notesapp.provider/NotesProvider"
        private const val URL = "content://$PROVIDER_NAME/NOTES"
        val CONTENT_URI = Uri.parse(URL)!!
        const val COLUMN_TITLE = "TITLE"
        const val COLUMN_NOTE = "NOTE"
        const val COLUMN_COLOR = "COLOR"
        const val COLUMN_ID = "ID"
    }

    private lateinit var db: SQLiteDatabase



    override fun onCreate(): Boolean {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, cv: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun update(uri: Uri, cv: ContentValues?, condition: String?, conditionValues: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, condition: String?, conditionValues: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun query(
        uri: Uri,
        columns: Array<out String>?,
        condition: String?,
        conditionValues: Array<out String>?,
        order: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }
}