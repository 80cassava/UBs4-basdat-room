package com.example.bdm_room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(MhsEntity::class), version = 1)
abstract class MhsDatabase : RoomDatabase() {
    abstract fun mhsDao(): MhsDao

    companion object {
        @Volatile
        private var instance: MhsDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            MhsDatabase::class.java, "mhs.db"
        ).build()
    }
}