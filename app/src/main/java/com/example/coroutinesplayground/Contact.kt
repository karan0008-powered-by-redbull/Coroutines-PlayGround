package com.example.coroutinesplayground

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val colorAssociated : Int)