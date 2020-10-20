package com.example.neostore.table.Roomdbfav

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.neostore.table.Tabledata


@Database(entities = [Tabledata::class], version = 1)
abstract class favDB : RoomDatabase() {
    abstract fun favoriteDao(): favDao
}
/*
@Database(entities = [Address::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun AddressDao(): AddressDao
 */