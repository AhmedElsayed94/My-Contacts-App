package com.ahmedelsayed.mycontacts.ui.addcontact.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmedelsayed.mycontacts.data.model.ContactRealmObject
import io.realm.Realm


/**
 * Created by Ahmed Elsayed on 27-Nov-19.
 */
class AddContactViewModel : ViewModel() {
    private val realm: Realm = Realm.getDefaultInstance()
    var status = MutableLiveData<Boolean?>()


    fun addContact(name: String, phone: String) {
        if (findAll(phone).isNullOrEmpty()) {
            addContactToDataBase(name, phone)
            setIsSavedValue(true)
        } else {
            setIsSavedValue(false)
        }
    }

    private fun findAll(phone: String): List<ContactRealmObject> {
        return realm.where(ContactRealmObject::class.java!!).equalTo("number", phone).findAll()
    }

    private fun addContactToDataBase(name: String, phone: String) {
        realm.executeTransactionAsync({
            val contact = it.createObject(ContactRealmObject::class.java, phone)
            contact.name = name
        }, {
            // success
        }, {
            // error
        })
    }

    private fun setIsSavedValue(state: Boolean) {
        status.value = state
    }


}