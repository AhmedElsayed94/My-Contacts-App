package com.ahmedelsayed.mycontacts.base

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by Ahmed Elsayed on 26-Nov-19.
 */

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize Realm. Should only be done once when the application starts.
        Realm.init(this)
        val config = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(config)
    }
}