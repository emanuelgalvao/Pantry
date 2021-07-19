package com.emanuelgalvao.pantry.service.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.emanuelgalvao.pantry.service.model.User

@Database(entities = [User::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun userDAO(): UserDAO

    companion object {
        private lateinit var INSTANCE: LocalDatabase

        fun getDatabase(context: Context): LocalDatabase {
            if (!Companion::INSTANCE.isInitialized) {
                synchronized(LocalDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context, LocalDatabase::class.java, "pantryDB")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }

}