package com.example.neostore.table.Roomdbfav

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.neostore.table.Tabledata

@Dao

interface favDao {
    @Insert
    fun addData(favoriteList: Tabledata?)

    @get:Query("select * from favoritelist")
    val favoriteData: List<Tabledata>?

    @Query("SELECT EXISTS (SELECT 1 FROM favoritelist WHERE id=:id)")
    fun isFavorite(id: Int): Int

    @Delete
    fun delete(favoriteList: Tabledata?)
    /*
    @Dao
interface FavoriteDao {
    @Insert
    fun addData(favoriteList: Tabledata?)

    @Query("select * from favoritelist")
    val favoriteData: List<Tabledata>?

    @Query("SELECT EXISTS (SELECT 1 FROM favoritelist WHERE id=:id)")
    fun isFavorite(id: Int): Int

    @Delete
    fun delete(favoriteList: Tabledata?)
}
     */
}