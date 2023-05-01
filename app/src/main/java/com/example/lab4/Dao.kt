package com.example.lab4

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@androidx.room.Dao
interface Dao {
    @Insert
    fun insertItem(item: Item)
    @Query("SELECT * FROM items")
    fun getAllItems():Flow<List<Item>>
    @Query("SELECT * FROM items WHERE id = :id")
    fun getElem(id:Int):Item
    @Delete
    fun deleteItem(item: Item)
}