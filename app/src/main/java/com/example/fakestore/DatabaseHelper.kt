package com.example.fakestore

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context,"productsv",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE \"products\" (\n" +
                "\t\"id\"\tINTEGER ,\n" +
                "\t\"title\"\tTEXT ,\n" +
                "\t\"price\"\tINTEGER ,\n" +
                "\t\"description\"\tTEXT ,\n" +
                "\t\"images\"\tTEXT ,\n" +
                "\t\"creationAt\"\tTEXT ,\n" +
                "\t\"updatedAt\"\tTEXT ,\n" +
                "\t\"category\"\tINTEGER ,\n" +
                "\tPRIMARY KEY(\"id\" ),\n" +
                "\tFOREIGN KEY(\"category\") REFERENCES \"categories\"(\"id\")\n" +
                ")")

        db?.execSQL("CREATE TABLE \"productsCart\" (\n" +
                "\t\"id\"\tINTEGER ,\n" +
                "\t\"title\"\tTEXT ,\n" +
                "\t\"price\"\tINTEGER ,\n" +
                "\t\"description\"\tTEXT ,\n" +
                "\t\"images\"\tTEXT ,\n" +
                "\t\"creationAt\"\tTEXT ,\n" +
                "\t\"updatedAt\"\tTEXT ,\n" +
                "\t\"category\"\tINTEGER ,\n" +
                "\tPRIMARY KEY(\"id\" ),\n" +
                "\tFOREIGN KEY(\"category\") REFERENCES \"categories\"(\"id\")\n" +
                ")")

        db?.execSQL("CREATE TABLE \"myPrefs\" (\n" +
                "\t\"language\"\tTEXT,\n" +
                "\t\"nightmode\"\tINTEGER\n" +
                ")")


    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS products")
        db?.execSQL("DROP TABLE IF EXISTS productsCart")
        db?.execSQL("DROP TABLE IF EXISTS myPrefs")
        onCreate(db)
    }
}