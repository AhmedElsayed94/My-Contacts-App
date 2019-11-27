package com.ahmedelsayed.mycontacts.ui.contactdetails.view


import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProviders
import com.ahmedelsayed.mycontacts.R
import com.ahmedelsayed.mycontacts.base.BaseFragment
import com.ahmedelsayed.mycontacts.data.model.ContactModel
import com.ahmedelsayed.mycontacts.ui.contactdetails.viewmodel.ContactDetailsViewModel
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.app_toolbar_normal.*
import kotlinx.android.synthetic.main.fragment_contact_details.*
import kotlin.math.abs


/**
 * Created by Ahmed Elsayed on 25-Nov-19.
 */
class ContactDetailsFragment : BaseFragment() {

    lateinit var contact: ContactModel
    lateinit var phoneNumber: String
    lateinit var contactDetailsViewModel: ContactDetailsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contact = arguments!!.getParcelable("contact")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_contact_details, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactDetailsViewModel =
            ViewModelProviders.of(this).get(ContactDetailsViewModel::class.java)
        fetchData()
        setupListeners()
    }

    private fun fetchData() {
        toolbar_title.text = contact.name
        name_tv.text = contact.name
        contact_number_text_view.text = contact.number
        phoneNumber = contact.number
    }

    private fun setupListeners() {
        setToolbarTitleVisibility();
        backButton.setOnClickListener {
            activity!!.onBackPressed()
        }
        contact_card_view.setOnClickListener {
            checkPhonePermissionThenCall(contact.number)
        }
        delete_button.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(context!!)
            dialogBuilder.setMessage(getString(R.string.do_you_want_to_delete))
                .setCancelable(true)
                .setPositiveButton(
                    getString(R.string.proceed),
                    DialogInterface.OnClickListener { dialog, id ->
                        deleteContact()
                        dialog.cancel()
                    })
                .setNegativeButton(
                    getString(R.string.cancel),
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })

            val alert = dialogBuilder.create()
            alert.setTitle(getString(R.string.warning))
            alert.show()
        }
    }

    private fun deleteContact() {
        contactDetailsViewModel.deleteContact(contact.number)
        activity!!.onBackPressed()
    }


    private fun phoneCall(phoneNumber: String) {
        if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
            startActivity(intent)
        } else {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == 42) {
            phoneCall(phoneNumber)
            return
        }
    }

    private fun checkPhonePermissionThenCall(phoneNumber: String) {
        if (Build.VERSION.SDK_INT < 23) {
            phoneCall(phoneNumber)
        } else {
            if (ActivityCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                phoneCall(phoneNumber)
            } else {
                //Asking request Permissions
                requestPermissions(
                    arrayOf(
                        Manifest.permission.CALL_PHONE
                    ), 42
                )
            }
        }

    }

    private fun setToolbarTitleVisibility() {
        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) { //  Collapsed
                toolbar_title.visibility = View.VISIBLE
                toolbar_icon.visibility = View.VISIBLE
            } else { //Expanded
                toolbar_title.visibility = View.GONE
                toolbar_icon.visibility = View.GONE
            }
        })
    }


}
