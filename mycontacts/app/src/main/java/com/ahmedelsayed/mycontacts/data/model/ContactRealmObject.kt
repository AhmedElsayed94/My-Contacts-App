package com.ahmedelsayed.mycontacts.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by Ahmed Elsayed on 26-Nov-19.
 */
/*
 Separated the Contact Realm Object from Contact Model due to a
 problem caused by Filterable in the Recycler View "can not get the data from the list"
 the only solution with out the separation was to use realm Rv or remove filterable or just use a search query

 Note : what i did is just a work around due to lack of time
 */
open class ContactRealmObject(
    var name: String = "",
    @PrimaryKey var number: String = ""
) : RealmObject()