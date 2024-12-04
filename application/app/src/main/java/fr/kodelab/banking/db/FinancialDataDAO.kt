package fr.kodelab.banking.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import fr.kodelab.banking.model.FinancialData

class FinancialDataDAO(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    fun addFinancialData(financialData: FinancialData): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_USER_ID, financialData.userId)
            put(DatabaseHelper.COLUMN_AMOUNT, financialData.amount)
            put(DatabaseHelper.COLUMN_TAG, financialData.tag)
            put(DatabaseHelper.COLUMN_DATE, financialData.date)
            put(DatabaseHelper.COLUMN_LOCATION, financialData.location)
        }
        return db.insert(DatabaseHelper.TABLE_FINANCIAL_DATA, null, values)
    }

    fun getFinancialDataByUserId(userId: Long): List<FinancialData> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            DatabaseHelper.TABLE_FINANCIAL_DATA,
            arrayOf(DatabaseHelper.COLUMN_FINANCIAL_ID, DatabaseHelper.COLUMN_USER_ID, DatabaseHelper.COLUMN_AMOUNT, DatabaseHelper.COLUMN_TAG, DatabaseHelper.COLUMN_DATE, DatabaseHelper.COLUMN_LOCATION),
            "${DatabaseHelper.COLUMN_USER_ID}=?",
            arrayOf(userId.toString()),
            null,
            null,
            null
        )
        val financialDataList = mutableListOf<FinancialData>()
        while (cursor.moveToNext()) {
            financialDataList.add(
                FinancialData(
                    cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_FINANCIAL_ID)),
                    cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_ID)),
                    cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_AMOUNT)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TAG)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LOCATION))
                )
            )
        }
        cursor.close()
        return financialDataList
    }

    fun deleteAllFinancialData() {
        val db = dbHelper.writableDatabase
        db.delete(DatabaseHelper.TABLE_FINANCIAL_DATA, null, null)
    }

    // Other methods...
}
