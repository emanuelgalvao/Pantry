package com.emanuelgalvao.pantry.service.repository.local

import androidx.room.*
import com.emanuelgalvao.pantry.service.model.PantryItem

@Dao
interface PantryItemDAO {

    @Insert
    fun save(item: PantryItem): Long

    @Delete
    fun delete(item: PantryItem): Int

    @Query("SELECT * FROM pantry_item ORDER BY DATE(due_date) ASC")
    fun findAll(): List<PantryItem>

    @Query("SELECT * FROM pantry_item ORDER BY DATE(due_date) ASC LIMIT 5")
    fun findItemsHome(): List<PantryItem>
}