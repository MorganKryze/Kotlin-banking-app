package fr.kodelab.banking.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "banking.db"
        const val DATABASE_VERSION = 2 // Increment this version number when you make schema changes

        const val TABLE_USERS = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PIN = "pin"
        const val COLUMN_LAST_LOGIN = "last_login"
        const val COLUMN_CREATION_DATE = "creation_date"

        const val TABLE_FINANCIAL_DATA = "financial_data"
        const val COLUMN_FINANCIAL_ID = "id"
        const val COLUMN_USER_ID = "user_id"
        const val COLUMN_AMOUNT = "amount"
        const val COLUMN_TAG = "tag"
        const val COLUMN_DATE = "date"
        const val COLUMN_LOCATION = "location"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_USERS_TABLE = ("CREATE TABLE $TABLE_USERS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_NAME TEXT,"
                + "$COLUMN_PIN TEXT,"
                + "$COLUMN_LAST_LOGIN TEXT,"
                + "$COLUMN_CREATION_DATE TEXT" + ")")
        db.execSQL(CREATE_USERS_TABLE)

        val CREATE_FINANCIAL_DATA_TABLE = ("CREATE TABLE $TABLE_FINANCIAL_DATA ("
                + "$COLUMN_FINANCIAL_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_USER_ID INTEGER,"
                + "$COLUMN_AMOUNT REAL,"
                + "$COLUMN_TAG TEXT,"
                + "$COLUMN_DATE TEXT,"
                + "$COLUMN_LOCATION TEXT,"
                + "FOREIGN KEY($COLUMN_USER_ID) REFERENCES $TABLE_USERS($COLUMN_ID)" + ")")
        db.execSQL(CREATE_FINANCIAL_DATA_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE $TABLE_USERS ADD COLUMN $COLUMN_LAST_LOGIN TEXT")
            db.execSQL("ALTER TABLE $TABLE_USERS ADD COLUMN $COLUMN_CREATION_DATE TEXT")
        }
        // Add more upgrade steps as needed
    }
}
