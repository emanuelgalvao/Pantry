package com.emanuelgalvao.pantry.service.repository.local

import androidx.room.*
import com.emanuelgalvao.pantry.service.model.ShoppingItem

@Dao
interface ShoppingItemDAO {

    @Insert
    fun save(item: ShoppingItem): Long

    @Update
    fun update(item: ShoppingItem): Int

    @Delete
    fun delete(item: ShoppingItem): Int

    @Query("SELECT * FROM shopping_item ORDER BY checked, description")
    fun findAll(): List<ShoppingItem>

    @Query("SELECT * FROM shopping_item ORDER BY checked ASC LIMIT 5")
    fun findItemsHome(): List<ShoppingItem>
}