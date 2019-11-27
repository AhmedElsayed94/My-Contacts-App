package com.ahmedelsayed.mycontacts.base

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.ahmedelsayed.mycontacts.ui.main.view.MainActivity

/**
 * Created by Ahmed Elsayed on 27-Nov-19.
 */

open class BaseFragment : Fragment() {

    lateinit var mView: View

    fun hideKeyboard() {
        val activity = activity as MainActivity
        val view = activity.currentFocus
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view!!.windowToken, 0)
        }
    }

    fun hideView(view: View) {
        view.visibility = View.GONE
    }

    fun showView(view: View) {
        view.visibility = View.VISIBLE
    }


}