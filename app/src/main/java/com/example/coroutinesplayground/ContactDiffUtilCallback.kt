package com.example.coroutinesplayground

import androidx.recyclerview.widget.DiffUtil

class ContactDiffUtilCallback(
    var oldList : List<Contact>,
    var newList : List<Contact>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
    = oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].name == newList[newItemPosition].name

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}