package com.example.coroutinesplayground

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface ContactsDao {

    /*@Query("SELECT * FROM Contact ORDER BY name COLLATE NOCASE ASC")
    fun allContactsByNamePaged(): PagingSource<Int, Contact>*/

    @Query("SELECT * FROM Contact ORDER BY name COLLATE NOCASE ASC")
    fun allContactsByNameList(): List<Contact>

    @Query("SELECT * FROM Contact WHERE isFavourite = 1")
    fun fetchFavouriteContacts() : List<Contact>

    @Insert
    fun insert(cheeses: List<Contact>)

    @Insert
    fun insert(cheese: Contact)

    @Delete
    fun delete(cheese: Contact)

    @Update
    fun update(cheese: Contact)
}