package com.rodolfozamora.iu.contact.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.rodolfozamora.data.model.Contact

import com.rodolfozamora.R;

class ContactListAdapter (context: Context, resource: Int) : ArrayAdapter<Contact>(context, resource) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (convertView == null) {
            Log.d("LIST_ADAPTER", "convert view")
            val inflater = LayoutInflater.from(parent!!.context)
            view = inflater.inflate(R.layout.contact_item, null)
        }

        val contact = this.getItem(position)
        view?.findViewById<TextView>(R.id.txtNameContactItem).apply {
            this?.text = contact?.name.plus(" ").plus(contact?.lastName)
        }

        view?.findViewById<TextView>(R.id.txtPhoneNumberContactItem).apply {
            this?.text = contact?.phoneNumber
        }

        return view!!
    }

}