package com.development.pega.gastodeenergia.database

import android.content.ContentValues
import android.content.Context
import com.development.pega.gastodeenergia.constants.AppConstants
import com.development.pega.gastodeenergia.model.ElectronicObject
import java.lang.Exception

class Repository private constructor(context: Context) {

    private var mDatabaseHelper = DatabaseHelper(context)

    companion object {
        private lateinit var repository: Repository

        fun getInstance(context: Context): Repository {
            if (!::repository.isInitialized) {
                repository = Repository(context)
            }
            return repository
        }
    }

    fun save(electronicOjb: ElectronicObject): Boolean {
        return try {

            val db = mDatabaseHelper.writableDatabase

            val contentValues = ContentValues()
            contentValues.put(AppConstants.OBJECT_DATABASE.COLUMNS.NAME, electronicOjb.name)
            contentValues.put(AppConstants.OBJECT_DATABASE.COLUMNS.WATTS, electronicOjb.watts)
            contentValues.put(AppConstants.OBJECT_DATABASE.COLUMNS.USED_HOURS,electronicOjb.usedHours
            )

            db.insert(AppConstants.OBJECT_DATABASE.TABLE_NAME, null, contentValues)

            true
        } catch (e: Exception) {
            false
        }
    }

    fun update(electronicOjb: ElectronicObject): Boolean {
        return try {

            val db = mDatabaseHelper.writableDatabase

            val contentValues = ContentValues()
            contentValues.put(AppConstants.OBJECT_DATABASE.COLUMNS.NAME, electronicOjb.name)
            contentValues.put(AppConstants.OBJECT_DATABASE.COLUMNS.WATTS, electronicOjb.watts)
            contentValues.put(AppConstants.OBJECT_DATABASE.COLUMNS.USED_HOURS, electronicOjb.usedHours)

            val selection = AppConstants.OBJECT_DATABASE.COLUMNS.ID + " = ?"
            val args = arrayOf(electronicOjb.id.toString())

            db.update(AppConstants.OBJECT_DATABASE.TABLE_NAME, contentValues, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun delete(id: Int): Boolean {
        return try {

            val db = mDatabaseHelper.writableDatabase

            val selection = AppConstants.OBJECT_DATABASE.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            db.delete(AppConstants.OBJECT_DATABASE.TABLE_NAME, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun queryObject(): List<ElectronicObject> {

        val list: MutableList<ElectronicObject> = ArrayList()

        return try {
            val db = mDatabaseHelper.readableDatabase

            val projection = arrayOf(
                AppConstants.OBJECT_DATABASE.COLUMNS.ID,
                AppConstants.OBJECT_DATABASE.COLUMNS.NAME,
                AppConstants.OBJECT_DATABASE.COLUMNS.WATTS,
                AppConstants.OBJECT_DATABASE.COLUMNS.USED_HOURS
            )

            val cursor = db.query(
                AppConstants.OBJECT_DATABASE.TABLE_NAME,
                projection, null, null, null, null, null
            )

            if(cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(AppConstants.OBJECT_DATABASE.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(AppConstants.OBJECT_DATABASE.COLUMNS.NAME))
                    val watts = cursor.getInt(cursor.getColumnIndex(AppConstants.OBJECT_DATABASE.COLUMNS.WATTS))
                    val usedHours = cursor.getString(cursor.getColumnIndex(AppConstants.OBJECT_DATABASE.COLUMNS.USED_HOURS)) // in minutes

                    list.add(ElectronicObject(id, name, watts, usedHours))
                }
            }

            cursor?.close()
            list
        } catch (e: Exception) {
            list
        }
    }

    fun getObj(id: Int): ElectronicObject? {

        var electronicOjb: ElectronicObject? = null

        return try {
            val db = mDatabaseHelper.readableDatabase

            val projection = arrayOf(
                AppConstants.OBJECT_DATABASE.COLUMNS.ID,
                AppConstants.OBJECT_DATABASE.COLUMNS.NAME,
                AppConstants.OBJECT_DATABASE.COLUMNS.WATTS,
                AppConstants.OBJECT_DATABASE.COLUMNS.USED_HOURS
            )

            val selection = AppConstants.OBJECT_DATABASE.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            val cursor = db.query(
                AppConstants.OBJECT_DATABASE.TABLE_NAME,
                projection, selection, args, null, null, null
            )

            if(cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(AppConstants.OBJECT_DATABASE.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(AppConstants.OBJECT_DATABASE.COLUMNS.NAME))
                    val watts = cursor.getInt(cursor.getColumnIndex(AppConstants.OBJECT_DATABASE.COLUMNS.WATTS))
                    val usedHours = cursor.getString(cursor.getColumnIndex(AppConstants.OBJECT_DATABASE.COLUMNS.USED_HOURS)) // in minutes

                    electronicOjb = ElectronicObject(id, name, watts, usedHours)
                }
            }

            cursor?.close()
            electronicOjb
        } catch (e: Exception) {
            electronicOjb
        }
    }
}