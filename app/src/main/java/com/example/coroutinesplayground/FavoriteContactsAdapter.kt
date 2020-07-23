package com.example.coroutinesplayground

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.coroutinesplayground.FavoriteContactsAdapter.*
import java.util.ArrayList

class FavoriteContactsAdapter(
    private var context : Context,
    private var listener: OnFavouriteContactClicked?
) : RecyclerView.Adapter<FavoriteContactViewHolder>() {

    private val contactsList = ArrayList<Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteContactViewHolder {
        return FavoriteContactViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.favourites_view, parent, false)
        )
    }

    fun addAll(updatedList : List<Contact>){
        contactsList.addAll(updatedList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    override fun onBindViewHolder(holder: FavoriteContactViewHolder, position: Int) {
        holder.bindData(contactsList[position],context)
        holder.ivFav.setOnClickListener {
            contactsList[position].isFavourite = !contactsList[position].isFavourite
            if(contactsList[position].isFavourite)
                holder.ivFav.setImageDrawable(ContextCompat.getDrawable(context,
                    R.drawable.ic_baseline_star_24))
            else
                holder.ivFav.setImageDrawable(ContextCompat.getDrawable(context,
                    R.drawable.ic_baseline_star_border_24))
            listener?.onStarClicked(contactsList[position],position)
            notifyDataSetChanged()
        }
    }

    fun removeFavouriteInstant(contact: Contact,position: Int){
        contactsList.remove(contact)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position,contactsList.size)
    }


    class FavoriteContactViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val ivCheck = view.findViewById(R.id.ivCheck) as ImageView
        val clView = view.findViewById(R.id.clView) as ConstraintLayout
        val ivFav = view.findViewById(R.id.ivFav) as ImageView
        var contact : Contact? = null
        private val tvNameInitials = view.findViewById(R.id.tvNameInitials) as TextView
        private val tvName = view.findViewById(R.id.tvName) as TextView
        fun bindData(contact : Contact, context: Context){
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
            if(contact.isFavourite)
                ivFav.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_baseline_star_24))
            else
                ivFav.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_baseline_star_border_24))
            /*tvNameInitials.setBackgroundColor(ContextCompat.getColor(
                context,
                R.color.colorPrimary))*/
        }
    }

    interface OnFavouriteContactClicked{
        fun onStarClicked(contact: Contact,position: Int)
    }

}