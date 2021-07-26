package com.emanuelgalvao.pantry.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pantry_item")
class PantryItem {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "description")
    var description: String = ""

    @ColumnInfo(name = "due_date")
    var dueDate: String = ""

}