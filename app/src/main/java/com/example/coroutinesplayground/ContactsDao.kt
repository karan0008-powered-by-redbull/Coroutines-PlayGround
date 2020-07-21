package com.example.coroutinesplayground

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ContactsDao {

    /*@Query("SELECT * FROM Contact ORDER BY name COLLATE NOCASE ASC")
    fun allContactsByNamePaged(): PagingSource<Int, Contact>*/

    @Query("SELECT * FROM Contact ORDER BY name COLLATE NOCASE ASC")
    fun allContactsByNameList(): List<Contact>

    @Insert
    fun insert(cheeses: List<Contact>)

    @Insert
    fun insert(cheese: Contact)

    @Delete
    fun delete(cheese: Contact)
}