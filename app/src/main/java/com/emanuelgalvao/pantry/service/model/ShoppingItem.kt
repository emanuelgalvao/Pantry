package com.emanuelgalvao.pantry.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_item")
class ShoppingItem {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "description")
    var description: String = ""

    @ColumnInfo(name = "quantity")
    var quantity: String = ""

    @ColumnInfo(name = "checked")
    var buyed: Boolean = false
}