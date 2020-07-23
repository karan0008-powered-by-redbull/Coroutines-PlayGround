package com.example.coroutinesplayground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coroutinesplayground.databinding.ActivityFavouritesBinding

class FavouritesActivity : AppCompatActivity(), FavoriteContactsAdapter.OnFavouriteContactClicked {

    lateinit var favoritesBinding : ActivityFavouritesBinding
    lateinit var favAdapter: FavoriteContactsAdapter
    lateinit var contactsViewModel: ContactsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoritesBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_favourites
        )
        setSupportActionBar(favoritesBinding.toolbar)
        contactsViewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)
        setupRecyclerViewAndAdapter()
        observeContactsLiveData()
        contactsViewModel.getFavourites()
    }

    private fun setupRecyclerViewAndAdapter(){
        favAdapter = FavoriteContactsAdapter(this,this)
        favoritesBinding.rvContacts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favAdapter
        }
    }

    private fun observeContactsLiveData(){
        contactsViewModel.getFavContactsLiveData().observe(this,
            Observer<List<Contact>>{
                favoritesBinding.pbContacts.visibility = View.GONE
                favAdapter.addAll(it)
            })
    }

    override fun onStarClicked(contact: Contact, position :Int) {
        contactsViewModel.updateContact(contact)
        favAdapter.removeFavouriteInstant(contact,position)
        Toast.makeText(this,contact.name+" has been removed from favourites",Toast.LENGTH_SHORT).show()
    }

}