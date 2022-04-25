package com.rodolfozamora.iu.contact

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rodolfozamora.R
import com.rodolfozamora.data.model.Contact
import com.rodolfozamora.iu.contact.adapter.ContactListAdapter

class ListContactFragment : Fragment() {

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
        val viewAdapter = ContactListAdapter(requireContext(), R.layout.contact_item)
        viewAdapter.addAll(loadAllContacts().toMutableList())

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

    //Todo: Delete this later
    private fun loadAllContacts(): Array<Contact> {
        val contacts = ArrayList<Contact>()
        for (i in 0..99)
            contacts.add(Contact(i + 1, i.toString(), "Rodolfo Zamora", "6647893218",
                "myaccout@email.com", ""))
        return contacts.toTypedArray()
    }
}