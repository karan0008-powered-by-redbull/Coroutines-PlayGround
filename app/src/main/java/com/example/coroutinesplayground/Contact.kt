package com.example.coroutinesplayground

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    var isFavourite : Boolean,
    val colorAssociated : Int)