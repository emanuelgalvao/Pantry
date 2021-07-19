package com.emanuelgalvao.pantry.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "user")
class User {

    @ColumnInfo(name = "uid")
    var uid: String = ""

    @ColumnInfo(name = "email")
    var email: String = ""

    @ColumnInfo(name = "name")
    var name: String = ""
}