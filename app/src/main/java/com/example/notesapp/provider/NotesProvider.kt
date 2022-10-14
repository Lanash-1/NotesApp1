package com.example.notesapp.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import com.example.notesapp.helper.NotesDBHelper

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
        val notesHelper = NotesDBHelper(context)
        db = notesHelper.writableDatabase
        return true
    }

    override fun insert(uri: Uri, cv: ContentValues?): Uri {
        db.insert("NOTES", null, cv)
        context?.contentResolver?.notifyChange(uri, null)
        return uri
    }

    override fun update(uri: Uri, cv: ContentValues?, condition: String?, conditionValues: Array<out String>?): Int {
        val count = db.update("NOTES", cv, condition, conditionValues)
        context?.contentResolver?.notifyChange(uri, null)
        return count
    }

    override fun delete(uri: Uri, condition: String?, conditionValues: Array<out String>?): Int {
        val count = db.delete("NOTES", condition, conditionValues)
        context?.contentResolver?.notifyChange(uri, null)
        return count
    }

    override fun query(
        uri: Uri,
        columns: Array<out String>?,
        condition: String?,
        conditionValues: Array<out String>?,
        order: String?
    ): Cursor? {
        return db.query("NOTES", columns, condition, conditionValues, null, null, order)
    }

    override fun getType(uri: Uri): String? {
        return "vnd.android.cursor.dir/vnd.example.PERSON"
    }
}