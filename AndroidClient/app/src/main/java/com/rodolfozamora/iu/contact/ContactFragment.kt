package com.rodolfozamora.iu.contact

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.rodolfozamora.R
import com.rodolfozamora.data.model.Contact

class ContactFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onStart() {
        super.onStart()

        val contact = arguments?.getSerializable("contact-object") as Contact

        val imgProfile = requireActivity().findViewById<ImageView>(R.id.imgContact)
        val txtNameContact = requireActivity().findViewById<TextView>(R.id.txtNameContact)
        val txtNumberContact = requireActivity().findViewById<TextView>(R.id.txtPhoneNumberContact)
        val txtEmailContact = requireActivity().findViewById<TextView>(R.id.txtEmailContact).apply {
            if (contact.email.isNotEmpty()) {
                this.visibility = View.VISIBLE
                this.text = contact.email
            }
        }

        txtNameContact.text = contact.name.plus(" ").plus(contact.lastName)
        txtNumberContact.text = contact.phoneNumber
        txtEmailContact.text = contact.email
        if (contact.imageProfile.isEmpty()) {
            imgProfile.setBackgroundColor(Color.parseColor("#1976D2"))
            imgProfile.setImageResource(R.drawable.ic_person)
        }

        val btnCall = requireActivity().findViewById<ImageButton>(R.id.btnCallPhoneContact)
        btnCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_CALL).apply {
                data = Uri.parse("tel:${contact.phoneNumber}")
            }
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            }
        }

        val btnEmail = requireActivity().findViewById<ImageButton>(R.id.btnEmailContact).apply {
            if (contact.email.isNotEmpty()) {
                this.visibility = View.VISIBLE
                this.isClickable = true
            }
        }
        btnEmail.setOnClickListener {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:${contact.email}")
                //putExtra(Intent.EXTRA_EMAIL, contact.email)
                //putExtra(Intent.EXTRA_SUBJECT, subject)
            }
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            }
        }
    }
}