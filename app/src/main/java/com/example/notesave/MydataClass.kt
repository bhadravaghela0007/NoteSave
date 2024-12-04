package com.example.notesave

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MydataClass(var context: Context) : SQLiteOpenHelper(context, "mydb.db",null, 1)
{
    override fun onCreate(db: SQLiteDatabase?) {

        var table ="CREATE TABLE note (id INTEGER Primary key autoincrement , title text , subtitle text)"
        db?.execSQL(table)

    }

    override fun onUpgrade(
        db: SQLiteDatabase?, oldVersion: Int, newVersion: Int
    ) {

    }

    fun insertData(title : String, subtitle : String)
    {
        var insert = " INSERT INTO note (title,subtitle) VALUES ('$title','$subtitle')"

        writableDatabase.execSQL(insert)
    }

    fun selectData(title: String,subtitle: String) : Cursor
    {
        var select = "SELECT * FROM note WHERE title = '$title', subtitle = '$subtitle'"

        var cursor = writableDatabase.rawQuery(select,null)

        return cursor
    }

}
