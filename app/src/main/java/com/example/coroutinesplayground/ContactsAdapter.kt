package com.example.coroutinesplayground

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ContactsAdapter(private var context : Context): RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {
    private val contactsList = ArrayList<Contact>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        return ContactsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.contacts_view,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bindData(contactsList[position])
    }

    fun addAll(updatedList : List<Contact>){
        contactsList.addAll(updatedList)
        notifyDataSetChanged()
    }

    fun removeContact(contact : Contact){
        contactsList.remove(contact)
        notifyDataSetChanged()
    }

    class ContactsViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val ivCheck = view.findViewById(R.id.ivCheck) as ImageView
        val clView = view.findViewById(R.id.clView) as ConstraintLayout
        var contact : Contact? = null
        private val tvNameInitials = view.findViewById(R.id.tvNameInitials) as TextView
        private val tvName = view.findViewById(R.id.tvName) as TextView
        fun bindData(contact : Contact){
            this.contact = contact
            tvName.text = contact.name
            val nameArray = contact.name.split(" ")
            val builder = StringBuilder()
            if (nameArray.size > 1) {
                builder.append(nameArray[0][0])
                builder.append(nameArray[1][0])
            } else builder.append(contact.name[0].toString())
            tvNameInitials.text = builder.toString()
            tvNameInitials.setBackgroundColor(contact.colorAssociated)
            /*tvNameInitials.setBackgroundColor(ContextCompat.getColor(
                context,
                R.color.colorPrimary))*/
        }
    }

}