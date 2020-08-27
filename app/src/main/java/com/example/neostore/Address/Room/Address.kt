package com.example.neostore.Address.Room

import androidx.room.ColumnInfo
import androidx.room.Entity

import androidx.room.PrimaryKey


/*@Entity(tableName = "favoritelist")
class Table {
    @PrimaryKey(autoGenerate = true)
    var id = 0


    @ColumnInfo(name = "address")
    var address: String? = null

}*/
@Entity(tableName = "address")
class Address {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @ColumnInfo(name = "address")
    var address: String? = null
}