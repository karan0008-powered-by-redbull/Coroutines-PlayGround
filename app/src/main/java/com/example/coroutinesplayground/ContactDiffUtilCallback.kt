package com.example.coroutinesplayground

import android.util.Log
import androidx.recyclerview.widget.DiffUtil

class ContactDiffUtilCallback : DiffUtil.Callback {

    private var oldList : List<Contact>? = null
    private var newList : List<Contact>? = null

    constructor(){
        Log.d("Normal","Statement")
    }


    constructor(oldList : List<Contact>,
                                newList : List<Contact>){
        this.oldList = oldList
        this.newList = newList
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
    = oldList!![oldItemPosition].id == newList!![newItemPosition].id

    override fun getOldListSize(): Int = oldList!!.size

    override fun getNewListSize(): Int = newList!!.size

    // This is where the manipulation will occur
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList!![oldItemPosition].isFavourite == newList!![newItemPosition].isFavourite

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}