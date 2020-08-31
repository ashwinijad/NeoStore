package com.example.neostore.Address.Room

import  androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


/*@Dao
interface AddressDao {
    @Insert
    fun addData(favoriteList: Table?)

    @get:Query("select * from favoritelist")
    val favoriteData: List<Table>?

    @Query("SELECT EXISTS (SELECT 1 FROM favoritelist WHERE id=:id)")
    fun isFavorite(id: Int): Int

    @Delete
    fun delete(favoriteList: Table?)
}*/
@Dao
interface AddressDao {
    @Insert
    suspend fun addData(address: Address)



    @Query("select * from address")
    fun getAddressesWithChanges() :LiveData<MutableList<Address>>

    @Query("SELECT EXISTS (SELECT 1 FROM address WHERE id=:id)")
    suspend fun isAddressAdded(id: Int): Int

    @Delete
     fun delete(address: Address)
  // @Query("DELETE FROM address WHERE id=:id")
  //  fun deleteAddress(id: Int): Int
@Query("DELETE from address")
fun delete()

}


