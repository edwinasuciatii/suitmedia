package suitmedia.co.id.ievents.classes.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DatabaseHelperData().databaseName, null, DatabaseHelperData().databaseVersion){

    override fun onCreate(dbLocal: SQLiteDatabase) {
        // ** CREATE DATABASE PORTAL ** //
        val sqlTrPortal = (" CREATE TABLE "+ DatabaseHelperData().tableEvents
                + " ( " + DatabaseHelperData().fieldId + " INTEGER PRIMARY KEY, "
                + DatabaseHelperData().fieldIdEvents + " VARCHAR(18), "
                + DatabaseHelperData().fieldEmail + " VARCHAR(50), "
                + DatabaseHelperData().fieldFirstName + " VARCHAR(50), "
                + DatabaseHelperData().fieldLastName + " VARCHAR(50), "
                + DatabaseHelperData().fieldAvatar + " TEXT );")
        dbLocal.execSQL(sqlTrPortal)
    }
    override fun onUpgrade(dbLocal: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sqlTrFuelPayment = " DROP TABLE IF EXISTS " + DatabaseHelperData().tableEvents
        dbLocal?.execSQL(sqlTrFuelPayment)
        dbLocal?.let { onCreate(it) }
    }
    // ** MODEL DATABASE ** //
    fun selectData(sql: String): Cursor{
        val dbLocal = this.readableDatabase
        return dbLocal.rawQuery(sql, null)
    }
    fun insertData(table: String, data: ContentValues): Boolean{
        val dbLocal = this.writableDatabase
        dbLocal.insert(table, null, data)
        dbLocal.close()
        return true
    }
    /*fun updateData(table: String, data: ContentValues, whereField: String, whereRecord: String): Boolean{
        val dbLocal = this.writableDatabase
        dbLocal.update(table, data, "$whereField = $whereRecord", null)
        dbLocal.close()
        return true
    }*/
    fun customQuery(query: String): Boolean{
        val dbLocal = this.writableDatabase
        dbLocal.execSQL(query)
        dbLocal.close()
        return true
    }
    fun deleteAllData(table: String): Boolean{
        val dbLocal = this.writableDatabase
        dbLocal.execSQL("DELETE FROM $table")
        dbLocal.close()
        return true
    }
}
