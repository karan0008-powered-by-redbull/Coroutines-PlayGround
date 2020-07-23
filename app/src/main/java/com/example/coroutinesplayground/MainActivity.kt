package com.example.coroutinesplayground

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coroutinesplayground.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ContactsAdapter.OnContactClickListener {

    private lateinit var bindingMainActivity: ActivityMainBinding
    lateinit var contactsViewModel: ContactsViewModel
    lateinit var contactsAdapter: ContactsAdapter
    lateinit var addDialog : AlertDialog
    //var contactsPagingAdapter = ContactPagingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMainActivity = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        setSupportActionBar(bindingMainActivity.toolbar)
        contactsViewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)

        bindingMainActivity.pbContacts.visibility = View.VISIBLE
        /*bindingMainActivity.rvContacts.adapter = contactsPagingAdapter
        lifecycleScope.launch {
            contactsViewModel.allContacts.collectLatest {
                contactsPagingAdapter.submitData(it) }
        }*/
        setupAlertDialog()
        setupRecyclerViewAndAdapter()
        setupSwipeHelper()
        observeContactsLiveData()
    }

    override fun onResume() {
        super.onResume()
        contactsViewModel.fetchAllContacts()
    }


    private fun setupRecyclerViewAndAdapter(){
        contactsAdapter = ContactsAdapter(this,this)
        bindingMainActivity.rvContacts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = contactsAdapter
        }
    }

    private fun setupAlertDialog(){
        val v = LayoutInflater.from(this).inflate(
            R.layout.add_contact,
            null
        )
        addDialog =
            AlertDialog.Builder(this).setView(v).create()
        val editText = v.findViewById(R.id.etContactName) as EditText
        val tvAdd = v.findViewById(R.id.tvAdd) as TextView
        tvAdd.setOnClickListener {
            if(editText.text.toString()!=""){
                bindingMainActivity.pbContacts.visibility = View.VISIBLE
                val contact = Contact(0,editText.text.toString(),false,0)
                contactsViewModel.insertContact(contact)
                contactsViewModel.fetchAllContacts()
                addDialog.dismiss()
            }
        }
    }

    private fun setupSwipeHelper(){
        ItemTouchHelper(object : ItemTouchHelper.Callback(){
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (viewHolder as ContactsAdapter.ContactsViewHolder).contact?.let {
                    contactsViewModel.removeContact(it)
                }
                contactsAdapter.removeContact(viewHolder.contact!!)
            }
        }).attachToRecyclerView(bindingMainActivity.rvContacts)
    }

    private fun observeContactsLiveData(){
        contactsViewModel.getContactsLiveData().observe(this,
        Observer<List<Contact>>{
            bindingMainActivity.pbContacts.visibility = View.GONE
            contactsAdapter.clearData()
            contactsAdapter.addAll(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.toolbar_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.add_contact ->{
                addDialog.show()
                return true
            }
            R.id.fav-> startActivity(Intent(this,FavouritesActivity::class.java))
        }
        return false
    }

    override fun onStarClicked(contact: Contact) {
        contactsViewModel.updateContact(contact)
    }

}