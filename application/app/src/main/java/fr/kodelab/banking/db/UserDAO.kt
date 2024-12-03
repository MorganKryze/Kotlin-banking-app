package fr.kodelab.banking.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import fr.kodelab.banking.model.User
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UserDAO(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    fun addUser(user: User): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NAME, user.name)
            put(DatabaseHelper.COLUMN_PIN, user.pin)
            put(DatabaseHelper.COLUMN_LAST_LOGIN, user.lastLogin)
            put(DatabaseHelper.COLUMN_CREATION_DATE, user.creationDate)
        }
        return db.insert(DatabaseHelper.TABLE_USERS, null, values)
    }

    fun getUserByUsername(username: String): User? {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            DatabaseHelper.TABLE_USERS,
            arrayOf(DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_PIN, DatabaseHelper.COLUMN_LAST_LOGIN, DatabaseHelper.COLUMN_CREATION_DATE),
            "${DatabaseHelper.COLUMN_NAME}=?",
            arrayOf(username),
            null,
            null,
            null
        )
        return if (cursor.moveToFirst()) {
            User(
                cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PIN)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LAST_LOGIN)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CREATION_DATE))
            )
        } else {
            null
        }
    }

    fun getAllUsers(): List<User> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            DatabaseHelper.TABLE_USERS,
            arrayOf(DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_PIN, DatabaseHelper.COLUMN_LAST_LOGIN, DatabaseHelper.COLUMN_CREATION_DATE),
            null,
            null,
            null,
            null,
            null
        )
        val users = mutableListOf<User>()
        while (cursor.moveToNext()) {
            users.add(
                User(
                    cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PIN)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LAST_LOGIN)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CREATION_DATE))
                )
            )
        }
        cursor.close()
        return users
    }

    fun updateLastLogin(userId: Long) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_LAST_LOGIN, getCurrentDateTime())
        }
        db.update(
            DatabaseHelper.TABLE_USERS,
            values,
            "${DatabaseHelper.COLUMN_ID}=?",
            arrayOf(userId.toString())
        )
    }

    fun deleteAllUsers() {
        val db = dbHelper.writableDatabase
        db.delete(DatabaseHelper.TABLE_USERS, null, null)
    }

    private fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }

    private fun parseDate(dateString: String): Date? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.parse(dateString)
    }

    // Other methods...
}
