package com.ahmedelsayed.mycontacts.ui.addcontact.view


import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ahmedelsayed.mycontacts.R
import com.ahmedelsayed.mycontacts.base.BaseFragment
import com.ahmedelsayed.mycontacts.ui.addcontact.viewmodel.AddContactViewModel
import kotlinx.android.synthetic.main.fragment_add_new_contact.*


/**
 * Created by Ahmed Elsayed on 25-Nov-19.
 */
class AddNewContactFragment : BaseFragment() {

    lateinit var addContactViewModel: AddContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_add_new_contact, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addContactViewModel = ViewModelProviders.of(this).get(AddContactViewModel::class.java)
        addContactViewModel.status.observe(this, Observer { status ->
            status?.let {
                //Reset status value at first to prevent multitriggering
                //and to be available to trigger action again
                addContactViewModel.status.value = null
                //Display Toast
                if (it)
                    Toast.makeText(
                        context,
                        getString(R.string.add_succsess),
                        Toast.LENGTH_SHORT
                    ).show()
                else
                    Toast.makeText(
                        context,
                        getString(R.string.add_failed),
                        Toast.LENGTH_SHORT
                    ).show()
            }
        })

        setupListeners()

    }

    private fun setupListeners() {
        toolbar_back_btn.setOnClickListener {
            hideKeyboard()
            activity!!.onBackPressed()
        }
        cancelButton.setOnClickListener {
            hideKeyboard()
            activity!!.onBackPressed()
        }
        saveButton.setOnClickListener {
            if (validateNameAndNumber()) {
                hideKeyboard()
                addContactViewModel.addContact(
                    input_name_edit_text.text.toString(),
                    input_number_edit_text.text.toString()
                )
                activity!!.onBackPressed()
            }
        }

    }


    private fun validateNameAndNumber(): Boolean {
        return when {
            TextUtils.isEmpty(input_name_edit_text.text) -> {
                input_name_edit_text.error = getString(R.string.name_is_needed)
                false
            }
            TextUtils.isEmpty(input_number_edit_text.text) -> {
                input_number_edit_text.error = getString(R.string.phone_is_needed)
                false
            }
            else -> true
        }
    }


}
