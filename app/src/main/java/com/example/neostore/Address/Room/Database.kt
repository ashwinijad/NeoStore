package com.example.neostore.Address.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Address::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun AddressDao(): AddressDao


   /* companion object {

        @Volatile
        private var INSTANCE: com.example.neostore.Address.Room.Database? = null

        fun getInstance(context: Context): com.example.neostore.Address.Room.Database {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        com.example.neostore.Address.Room.Database::class.java,
                        "database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }*/
}

