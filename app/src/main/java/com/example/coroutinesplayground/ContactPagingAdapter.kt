package com.example.coroutinesplayground

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ContactPagingAdapter : PagingDataAdapter<Contact, ContactPagingAdapter.ContactsViewHolder>(diffCallback) {

    class ContactsViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val ivCheck = view.findViewById(R.id.ivCheck) as ImageView
        val clView = view.findViewById(R.id.clView) as ConstraintLayout
        private val tvNameInitials = view.findViewById(R.id.tvNameInitials) as TextView
        private val tvName = view.findViewById(R.id.tvName) as TextView
        fun bindData(contact : Contact){
            tvName.text = contact.name
            val nameArray = contact.name.split(" ")
            val builder = StringBuilder()
            if (nameArray.size > 1) {
                builder.append(nameArray[0][0])
                builder.append(nameArray[1][0])
            } else builder.append(contact.name[0].toString())
            tvNameInitials.text = builder.toString()
            tvNameInitials.setBackgroundColor(Color.argb(255, Random().nextInt(256), Random().nextInt(256), Random().nextInt(256)))

        }
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bindData(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        return ContactsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.contacts_view, parent, false)
        )
    }

    companion object{
        private val diffCallback = object : DiffUtil.ItemCallback<Contact>() {
            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean =
                oldItem.id == newItem.id

            /**
             * Note that in kotlin, == checking on data classes compares all contents, but in Java,
             * typically you'll implement Object#equals, and use it to compare object contents.
             */
            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean =
                oldItem == newItem
        }
    }


}