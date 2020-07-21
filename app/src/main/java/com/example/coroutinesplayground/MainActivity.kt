package com.example.coroutinesplayground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coroutinesplayground.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var bindingMainActivity: ActivityMainBinding
    lateinit var contactsViewModel: ContactsViewModel
    lateinit var contactsAdapter: ContactsAdapter
    var contactsPagingAdapter = ContactPagingAdapter()

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
        setupRecyclerViewAndAdapter()
        setupSwipeHelper()
        observeContactsLiveData()
        contactsViewModel.fetchAllContacts()
    }

    private fun setupRecyclerViewAndAdapter(){
        contactsAdapter = ContactsAdapter(this)
        bindingMainActivity.rvContacts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = contactsAdapter
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
                Toast.makeText(this,"Adding new",Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }

}