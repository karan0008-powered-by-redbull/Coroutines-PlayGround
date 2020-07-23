package com.example.coroutinesplayground

import android.content.Context
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
        holder.bindData(contactsList[position],position,context)
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

    fun updateListUsingDiffCallback(newList : List<Contact>){
        val diffUtilCallback = ContactDiffUtilCallback(contactsList,newList)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        this.contactsList.clear()
        this.contactsList.addAll(newList)
        // Cheaper method to call than notifyDataSetChanged()
        diffResult.dispatchUpdatesTo(this)
    }

    fun removeContact(contact : Contact,position: Int){
        // Removing Using DiffUtil
        /*val newList = ArrayList<Contact>()
        val diffUtilCallback = ContactDiffUtilCallback(this.contactsList,newList)
        val result = DiffUtil.calculateDiff(diffUtilCallback)
        contactsList.remove(contact)
        result.dispatchUpdatesTo(this)*/
        contactsList.remove(contact)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position,contactsList.size)
    }



    class ContactsViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val ivCheck = view.findViewById(R.id.ivCheck) as ImageView
        val clView = view.findViewById(R.id.clView) as ConstraintLayout
        var pos = 0
        var contact : Contact? = null
        val ivFav = view.findViewById(R.id.ivFav) as ImageView
        private val tvNameInitials = view.findViewById(R.id.tvNameInitials) as TextView
        private val tvName = view.findViewById(R.id.tvName) as TextView
        fun bindData(contact : Contact, position: Int, context: Context){
            this.contact = contact
            this.pos = position
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

    companion object {
        /**
         * This diff callback informs the PagedListAdapter how to compute list differences when new
         * PagedLists arrive.
         * <p>
         * When you add a Cheese with the 'Add' button, the PagedListAdapter uses diffCallback to
         * detect there's only a single item difference from before, so it only needs to animate and
         * rebind a single view.
         *
         * @see DiffUtil
         */
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