package com.emanuelgalvao.pantry.service.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.emanuelgalvao.pantry.service.model.PantryItem
import com.emanuelgalvao.pantry.service.model.ShoppingItem

@Database(entities = [PantryItem::class, ShoppingItem::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun pantryItemDAO(): PantryItemDAO
    abstract fun shoppingItemDAO(): ShoppingItemDAO

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