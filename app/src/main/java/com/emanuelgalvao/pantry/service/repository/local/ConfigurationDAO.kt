package com.emanuelgalvao.pantry.service.repository.local

import androidx.room.*
import com.emanuelgalvao.pantry.service.model.Configuration

@Dao
interface ConfigurationDAO {

    @Insert
    fun save(configuration: Configuration): Long

    @Update
    fun update(configuration: Configuration): Int

    @Query("SELECT * FROM configuration WHERE id = 1")
    fun find(): Configuration
}