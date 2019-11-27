package com.ahmedelsayed.mycontacts.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmedelsayed.mycontacts.data.model.ContactModel
import com.ahmedelsayed.mycontacts.data.model.ContactRealmObject
import io.realm.Realm

/**
 * Created by Ahmed Elsayed on 26-Nov-19.
 */
class ContactViewModel : ViewModel() {
    val mContacts: MutableLiveData<List<ContactModel>> = MutableLiveData()
    private val realm: Realm = Realm.getDefaultInstance()

    fun getContacts() {
        val contacts: MutableList<ContactModel> = mutableListOf<ContactModel>()
        contacts.clear()
        contacts.addAll(getContactsFromDatabase())
        mContacts.value = contacts
    }


    private fun getContactsFromDatabase(): List<ContactModel> {
        val resultContact: List<ContactModel>
        realm.beginTransaction()
        val contacts = realm.where(ContactRealmObject::class.java).findAll()
        realm.commitTransaction()
        val contactModelList: MutableList<ContactModel> = mutableListOf<ContactModel>()
        for (contact in contacts) {
            contactModelList.add(ContactModel(contact.name, contact.number))
        }
        resultContact = contactModelList
        return resultContact
    }

}