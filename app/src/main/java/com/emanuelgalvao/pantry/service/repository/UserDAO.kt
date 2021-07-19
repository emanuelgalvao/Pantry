package com.emanuelgalvao.pantry.service.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.emanuelgalvao.pantry.service.model.User

@Dao
interface UserDAO {

    @Insert
    fun save(user: User)

    @Query("SELECT * FROM user LIMIT 1")
    fun find()

    @Query("DELETE FROM user")
    fun clear()
}