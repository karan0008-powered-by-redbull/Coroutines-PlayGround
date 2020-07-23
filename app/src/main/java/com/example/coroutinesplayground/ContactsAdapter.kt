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
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ContactsAdapter(private var context : Context,
                    private var listener: OnContactClickListener?
): RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {
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
        holder.bindData(contactsList[position],context)
        holder.ivFav.setOnClickListener {
            contactsList[position].isFavourite = !contactsList[position].isFavourite
            if(contactsList[position].isFavourite)
                holder.ivFav.setImageDrawable(ContextCompat.getDrawable(context,
                    R.drawable.ic_baseline_star_24))
            else
                holder.ivFav.setImageDrawable(ContextCompat.getDrawable(context,
                    R.drawable.ic_baseline_star_border_24))
            listener?.onStarClicked(contactsList[position])
            notifyDataSetChanged()
        }
    }

    fun updateList(newList : List<Contact>){
        val diffUtilCallback = ContactDiffUtilCallback(contactsList,newList)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        this.contactsList.clear()
        this.contactsList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun clearData(){
        contactsList.clear()
        notifyDataSetChanged()
    }

    fun addAll(updatedList : List<Contact>){
        contactsList.addAll(updatedList)
        notifyDataSetChanged()
    }

    fun addContact(contact: Contact){
        contactsList.add(contact)
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
        val ivFav = view.findViewById(R.id.ivFav) as ImageView
        private val tvNameInitials = view.findViewById(R.id.tvNameInitials) as TextView
        private val tvName = view.findViewById(R.id.tvName) as TextView
        fun bindData(contact : Contact, context: Context){
            this.contact = contact
            tvName.text = contact.name
            val nameArray = contact.name.split(" ")
            val builder = StringBuilder()
            if (nameArray.size > 1 && nameArray[0].isNotEmpty()) {
                builder.append(nameArray[0][0])
                builder.append(nameArray[1][0])
            } else builder.append(contact.name[0].toString())
            tvNameInitials.text = builder.toString()
            tvNameInitials.setBackgroundColor(contact.colorAssociated)
            if(contact.isFavourite)
                ivFav.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_baseline_star_24))
            else
                ivFav.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_baseline_star_border_24))
            /*tvNameInitials.setBackgroundColor(ContextCompat.getColor(
                context,
                R.color.colorPrimary))*/
        }
    }

    interface OnContactClickListener {
        fun onStarClicked(contact: Contact)
    }

}