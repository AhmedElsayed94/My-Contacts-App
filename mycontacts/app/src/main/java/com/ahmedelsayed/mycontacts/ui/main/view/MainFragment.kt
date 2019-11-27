package com.ahmedelsayed.mycontacts.ui.main.view


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmedelsayed.mycontacts.R
import com.ahmedelsayed.mycontacts.base.BaseFragment
import com.ahmedelsayed.mycontacts.data.model.ContactModel
import com.ahmedelsayed.mycontacts.ui.main.adapter.ContactsAdapter
import com.ahmedelsayed.mycontacts.ui.main.viewmodel.ContactViewModel
import kotlinx.android.synthetic.main.app_toolbar_main.*
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * Created by Ahmed Elsayed on 25-Nov-19.
 */
class MainFragment : BaseFragment(),
    ContactsAdapter.ContactAdapterInteractionListener {

    lateinit var navController: NavController
    lateinit var contactViewModel: ContactViewModel
    var contactsAdapter: ContactsAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_main, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)
        observeContactsChanges()
        getContacts()
        navController = Navigation.findNavController(view)
        setupListeners()
    }

    private fun setupListeners() {
        add_contact_btn.setOnClickListener {
            clearSearchText()
            hideKeyboard()
            navController.navigate(R.id.action_mainFragment_to_addNewContactFragment)
        }
        search_image_view.setOnClickListener {
            hideView(search_image_view)
            showView(close_search_image_view)
            showView(search_card_view)
        }
        close_search_image_view.setOnClickListener {
            clearSearchText()
            hideKeyboard()
            hideView(close_search_image_view)
            hideView(search_card_view)
            showView(search_image_view)
        }
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (contactsAdapter != null)
                    contactsAdapter!!.filter.filter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //no need
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //no need
            }
        })
    }

    private fun clearSearchText() {
        searchEditText.text.clear()
    }

    private fun getContacts() {
        contactViewModel.getContacts()
    }

    private fun observeContactsChanges() {
        contactViewModel.mContacts.observe(this, Observer<List<ContactModel>> {
            if (it.isNullOrEmpty()) {
                hideContactsLayout()
                if (contactsAdapter != null)
                    contactsAdapter!!.notifyDataSetChanged()
            } else {
                showContacts(it)
            }
        })
    }

    private fun hideContactsLayout() {
        hideView(my_contacts_recycler_view)
        hideView(search_image_view)
        showView(empty_layout)
    }

    private fun showContactsLayout() {
        hideView(empty_layout)
        showView(my_contacts_recycler_view)
        if (close_search_image_view.visibility == View.GONE) {
            showView(search_image_view)
        }
    }

    private fun showContacts(contacts: List<ContactModel>) {
        showContactsLayout()
        my_contacts_recycler_view.layoutManager = LinearLayoutManager(context)
        contactsAdapter = ContactsAdapter(contacts, this)
        my_contacts_recycler_view.adapter = contactsAdapter
    }


    override fun onContactClicked(contact: ContactModel) {
        clearSearchText()
        hideKeyboard()
        val bundle = bundleOf("contact" to contact)
        navController.navigate(R.id.action_mainFragment_to_contactDetailsFragment, bundle)
    }

    override fun setContactsCount(count: Int) {
        if (count == 0)
            showView(empty_layout)
        else
            hideView(empty_layout)
    }



}
