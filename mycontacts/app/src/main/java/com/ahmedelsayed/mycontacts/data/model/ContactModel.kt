package com.ahmedelsayed.mycontacts.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Ahmed Elsayed on 26-Nov-19.
 */
@Parcelize
data class ContactModel(val name: String, val number: String) : Parcelable