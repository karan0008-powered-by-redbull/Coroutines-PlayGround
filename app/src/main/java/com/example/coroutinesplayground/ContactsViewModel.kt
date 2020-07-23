package com.example.coroutinesplayground

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig

class ContactsViewModel(app:Application): AndroidViewModel(app) {
    private val contactsDB = ContactsDB.get(app).contactDao()

    private val contactsLiveData = MutableLiveData<List<Contact>>()

    fun getContactsLiveData() : LiveData<List<Contact>>{
        return contactsLiveData
    }

    private val favContactsLiveData = MutableLiveData<List<Contact>>()

    fun getFavContactsLiveData() : LiveData<List<Contact>>{
        return favContactsLiveData
    }

    fun fetchAllContacts() = ioThread {
        contactsLiveData.postValue(contactsDB.allContactsByNameList())
    }

    fun insertContact(contact : Contact) = ioThread{ contactsDB.insert(contact) }

    fun removeContact(contact: Contact) = ioThread {
        contactsDB.delete(contact)
    }

    fun updateContact(contact: Contact) = ioThread {
        contactsDB.update(contact)
    }

    fun getFavourites() = ioThread { favContactsLiveData.postValue(contactsDB.fetchFavouriteContacts()) }



    /*val allContacts = Pager(
        PagingConfig(
            pageSize = 10,
            enablePlaceholders = true,
            maxSize = 200
        )
    ) {
        contactsDB.allContactsByNamePaged()
    }.flow*/



}