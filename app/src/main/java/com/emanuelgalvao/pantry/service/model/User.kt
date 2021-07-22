package com.emanuelgalvao.pantry.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class User {

    @ColumnInfo(name = "uid")
    @PrimaryKey
    var uid: String = ""

    @ColumnInfo(name = "email")
    var email: String = ""

    @ColumnInfo(name = "name")
    var name: String = ""
}