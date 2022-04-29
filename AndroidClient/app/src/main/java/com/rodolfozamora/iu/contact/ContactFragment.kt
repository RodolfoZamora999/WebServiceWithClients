package com.rodolfozamora.iu.contact

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
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
import android.widget.Toast
import com.rodolfozamora.R
import com.rodolfozamora.data.model.Contact
import com.rodolfozamora.network.api.Response
import com.rodolfozamora.network.api.RestApiUtils
import com.rodolfozamora.persistence.JWT_TOKEN
import com.rodolfozamora.persistence.NAME_SHARED_PREFERENCES
import com.rodolfozamora.persistence.SERVER_ADDRESS

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
            if (!contact.email.isNullOrEmpty()) {
                this.visibility = View.VISIBLE
                this.text = contact.email
            }
        }

        txtNameContact.text = contact.name.plus(" ").plus(contact.lastName)
        txtNumberContact.text = contact.phoneNumber
        txtEmailContact.text = contact.email

        if (!contact.imageProfile.isNullOrEmpty()) {
            loadImage(contact.imageProfile!!, imgProfile)
        }
        else {
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
            if (!contact.email.isNullOrEmpty()) {
                this.visibility = View.VISIBLE
                this.isClickable = true
            }
        }
        btnEmail.setOnClickListener {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:${contact.email}")
            }
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            }
        }
    }

    private fun loadImage(relativePathImage: String, imageView: ImageView) {
        val preferences = requireActivity().getSharedPreferences(
            NAME_SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )
        val baseUrl = preferences.getString(SERVER_ADDRESS, "10.0.2.2")
        val url = "https://$baseUrl:8443"
        val jwtToken = preferences.getString(JWT_TOKEN, "null")

        val restApi = RestApiUtils(requireContext(), url, jwtToken)
        restApi.downloadImage(relativePathImage, object : RestApiUtils.ResponseCallback<Bitmap> {
            override fun result(response: Response<Bitmap>) {
                if (response.isResponseSuccessful()) {
                    requireActivity().runOnUiThread {
                        imageView.setImageBitmap(response.data)
                        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
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