package com.rodolfozamora.iu.contact

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rodolfozamora.R
import com.rodolfozamora.data.model.Contact
import com.rodolfozamora.iu.contact.adapter.ContactListAdapter
import com.rodolfozamora.network.api.RestApiUtils
import com.rodolfozamora.network.api.Response
import com.rodolfozamora.persistence.CURRENT_USER
import com.rodolfozamora.persistence.JWT_TOKEN
import com.rodolfozamora.persistence.NAME_SHARED_PREFERENCES
import com.rodolfozamora.persistence.SERVER_ADDRESS

class ListContactFragment : Fragment() {
    private var viewAdapter: ContactListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_contact, container, false)
    }

    override fun onStart() {
        super.onStart()

        val btnAddContact = requireActivity().findViewById<FloatingActionButton>(R.id.btnAddContact)
        btnAddContact.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_listContactFragment_to_registerContactFragment)
        }

        //ListView and view adapter
        viewAdapter = ContactListAdapter(requireContext(), R.layout.contact_item)

        val listView = requireActivity().findViewById<ListView>(R.id.listViewContacts)
        listView.adapter = viewAdapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val contact : Contact = parent.adapter.getItem(position) as Contact
            val data = Bundle()
            data.putLong("id-contact", id)
            data.putSerializable("contact-object", contact)
            Navigation.findNavController(view).navigate(R.id.action_listContactFragment_to_contactFragment, data)
        }
    }

    override fun onResume() {
        super.onResume()
        loadAllContacts()
    }

    private fun loadAllContacts() {
        //Load all contacts
        val preferences = requireActivity().getSharedPreferences(NAME_SHARED_PREFERENCES, MODE_PRIVATE)
        val serverAddress = preferences.getString(SERVER_ADDRESS, "10.0.2.2")
        val userId = preferences.getString(CURRENT_USER, "-1")
        val jwtToken = preferences.getString(JWT_TOKEN, "null")

        val restApi = RestApiUtils(requireContext(), "https://$serverAddress:8443", jwtToken)
        restApi.getAllContacts(userId!!.toInt(), object : RestApiUtils.ResponseCallback<Array<Contact>> {
            override fun result(response: Response<Array<Contact>>) {
                if (response.isResponseSuccessful()) {
                    //Update ListView
                    requireActivity().runOnUiThread {
                        viewAdapter?.addAll(response.data!!.toMutableList())
                    }
                }
                else {
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Something went wrong!", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
}