package com.example.coroutinesplayground

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig

public class ContactsViewModel(app:Application): AndroidViewModel(app) {
    private val contactsDB = ContactsDB.get(app).contactDao()

    private val contactsLiveData = MutableLiveData<List<Contact>>()

    fun getContactsLiveData() : LiveData<List<Contact>>{
        return contactsLiveData
    }

    fun fetchAllContacts() = ioThread {
        contactsLiveData.postValue(contactsDB.allContactsByNameList())
    }

    fun insertContact(contact : Contact) = ioThread{ contactsDB.insert(contact) }

    fun removeContact(contact: Contact) = ioThread {
        contactsDB.delete(contact)
    }


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