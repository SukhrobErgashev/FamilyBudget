package dev.sukhrob.familybudget.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.sukhrob.familybudget.data.model.Transaction

@Database(
    entities = [Transaction::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getTransactionDao(): TransactionDao
}
