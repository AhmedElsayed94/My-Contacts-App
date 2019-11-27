package com.ahmedelsayed.mycontacts.ui.contactdetails.viewmodel

import androidx.lifecycle.ViewModel
import com.ahmedelsayed.mycontacts.data.model.ContactRealmObject
import io.realm.Realm

/**
 * Created by Ahmed Elsayed on 27-Nov-19.
 */
class ContactDetailsViewModel : ViewModel() {
    private val realm: Realm = Realm.getDefaultInstance()

    fun deleteContact(number: String) {
        deleteContactFromDatabase(number)
    }

    private fun deleteContactFromDatabase(number: String) {
        realm.beginTransaction()
        val results = realm.where(ContactRealmObject::class.java)
            .equalTo("number", number).findAll()
        results.deleteAllFromRealm()
        realm.commitTransaction()
    }

}