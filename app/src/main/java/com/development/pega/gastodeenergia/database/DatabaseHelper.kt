package com.development.pega.gastodeenergia.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.development.pega.gastodeenergia.constants.AppConstants

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    companion object {
        private const val DATABASE_NAME = "eletronic.db"
        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE = ("create table " + AppConstants.OBJECT_DATABASE.TABLE_NAME + " ("
                + AppConstants.OBJECT_DATABASE.COLUMNS.ID + " integer primary key autoincrement, "
                + AppConstants.OBJECT_DATABASE.COLUMNS.NAME + " text, "
                + AppConstants.OBJECT_DATABASE.COLUMNS.WATTS + " integer, "
                + AppConstants.OBJECT_DATABASE.COLUMNS.USED_HOURS + " text);") //in minutes
    }

}