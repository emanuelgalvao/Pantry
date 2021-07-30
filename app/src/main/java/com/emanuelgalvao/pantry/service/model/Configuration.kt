package com.emanuelgalvao.pantry.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "configuration")
class Configuration {

    @ColumnInfo(name = "id")
    @PrimaryKey
    var id: Int = 1

    @ColumnInfo(name = "enable_flash")
    var enableFlash: Boolean = false

    @ColumnInfo(name = "due_days")
    var dueDays: Int = 2

    @ColumnInfo(name = "delete_shopping_item")
    var deleteShoppingItem: Boolean = false

    @ColumnInfo(name = "dark_mode")
    var darkMode: Boolean = false
}