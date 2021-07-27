package com.emanuelgalvao.pantry.service.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.emanuelgalvao.pantry.service.model.Configuration
import com.emanuelgalvao.pantry.service.model.PantryItem
import com.emanuelgalvao.pantry.service.model.ShoppingItem

@Database(entities = [PantryItem::class, ShoppingItem::class, Configuration::class], version = 2)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun pantryItemDAO(): PantryItemDAO
    abstract fun shoppingItemDAO(): ShoppingItemDAO
    abstract fun configurationDAO(): ConfigurationDAO

    companion object {
        private lateinit var INSTANCE: LocalDatabase

        fun getDatabase(context: Context): LocalDatabase {
            if (!Companion::INSTANCE.isInitialized) {
                synchronized(LocalDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context, LocalDatabase::class.java, "pantryDB")
                        .addMigrations(MIGRATION_1_2)
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE `configuration` (`id` INTEGER NOT NULL, `enable_flash` INT NOT NULL, `due_days` INT NOT NULL, `delete_shopping_item` INT NOT NULL, PRIMARY KEY(`id`))")
            }
        }
    }
}