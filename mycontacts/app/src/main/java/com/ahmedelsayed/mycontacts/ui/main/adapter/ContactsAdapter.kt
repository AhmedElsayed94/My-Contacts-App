package com.ahmedelsayed.mycontacts.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.ahmedelsayed.mycontacts.R
import com.ahmedelsayed.mycontacts.data.model.ContactModel
import kotlinx.android.synthetic.main.item_contact.view.*

/**
 * Created by Ahmed Elsayed on 25-Nov-19.
 */
class ContactsAdapter(
    private val contacts: List<ContactModel>,
    private val contactAdapterInteractionListener: ContactAdapterInteractionListener
) : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>(), Filterable {

    lateinit var filteredContactList: List<ContactModel>

    init {
        this.filteredContactList = contacts
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        return ContactsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_contact,
                    parent,
                    false
                )
        )
    }

    fun updateList(contactList: List<ContactModel>) {
        this.filteredContactList = contactList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        contactAdapterInteractionListener.setContactsCount(filteredContactList.size)
        return filteredContactList.size
    }


    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val contact = filteredContactList[position]
        holder.view.contact_name_text_view.text = contact.name
        holder.view.contact_number_text_view.text = contact.number
        holder.view.first_letter_text_view.text = contact.name?.get(0).toString()
        holder.view.setOnClickListener {
            contactAdapterInteractionListener.onContactClicked(contact)
        }
    }

    interface ContactAdapterInteractionListener {
        fun onContactClicked(contact: ContactModel)
        fun setContactsCount(count: Int)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charString: CharSequence?): FilterResults {
                val charSearch = charString.toString()
                if (charSearch.isEmpty()) {
                    filteredContactList = contacts
                } else {
                    val resultList = ArrayList<ContactModel>()
                    for (contact in contacts) {
                        if (contact.name.toLowerCase().contains(charSearch.toLowerCase())
                            || contact.number.contains(charSearch)
                        )
                            resultList.add(contact)
                    }
                    filteredContactList = resultList
                }
                val filterResults: FilterResults = Filter.FilterResults()
                filterResults.values = filteredContactList
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence?,
                filterResults: FilterResults?
            ) {
                if (filterResults!!.values != null) {
                    filteredContactList = filterResults.values as List<ContactModel>
                    updateList(filteredContactList)
                } else
                    updateList(contacts)
            }
        }
    }


    inner class ContactsViewHolder(val view: View) : RecyclerView.ViewHolder(view)


}