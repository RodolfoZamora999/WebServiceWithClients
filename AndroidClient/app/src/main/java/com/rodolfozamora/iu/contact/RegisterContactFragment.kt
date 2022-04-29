package com.rodolfozamora.iu.contact

import android.app.Activity.RESULT_OK
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.rodolfozamora.R
import com.rodolfozamora.data.model.Contact
import com.rodolfozamora.network.api.Response
import com.rodolfozamora.network.api.RestApiUtils
import com.rodolfozamora.persistence.*

class RegisterContactFragment : Fragment() {
    var imageProfileUri: Uri? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register_contact, container, false)
    }

    override fun onStart() {
        super.onStart()

        val btnCancel = requireActivity().findViewById<ImageButton>(R.id.btnCancelContactRegister)
        btnCancel.setOnClickListener {
            requireActivity().onBackPressed()
        }

        val btnRegister = requireActivity().findViewById<ImageButton>(R.id.btnSaveContactRegister)
        btnRegister.setOnClickListener {
            val contact = loadContactDataFromIU()
            if (imageProfileUri != null) {
                //upload contact with a image
                 val uri = imageProfileUri
                registerContactWithImage(uri!!, contact)
            }
            else {
                //upload contact without a image
                registerContact(contact)
            }
        }

        val btnImage = requireActivity().findViewById<ImageButton>(R.id.btnImageProfileContactRegister)
        btnImage.setOnClickListener {
            chooseImage()
        }
    }

    private fun loadContactDataFromIU(): Contact {
        val activity = requireActivity()
        val txtName = activity.findViewById<EditText>(R.id.editNameContactRegister)
        val txtLastName = activity.findViewById<EditText>(R.id.editLastNameContactRegister)
        val txtNumber = activity.findViewById<EditText>(R.id.editPhoneNumberContactRegister)
        val txtEmail = activity.findViewById<EditText>(R.id.editEmailContactRegister)

        val name = txtName.text.toString()
        val lastName = txtLastName.text.toString()
        val phoneNumber = txtNumber.text.toString()
        val email: String? = if (txtEmail.text.isEmpty()) null else txtEmail.text.toString()

        return Contact(null, name, lastName, phoneNumber, email, null)
    }


    private fun chooseImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
            addCategory(Intent.CATEGORY_OPENABLE)
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/png", "image/jpeg"))
        }

        if (intent.resolveActivity(requireActivity().packageManager) != null)
            startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                imageProfileUri = data?.data
                requireActivity().findViewById<ImageView>(R.id.imgProfileContactRegister).apply {
                    setImageURI(imageProfileUri)
                    scaleType = ImageView.ScaleType.CENTER_CROP
                }
            }
        }
    }

    private fun registerContactWithImage(uri: Uri, contact: Contact) {
        val file = createFileFromAndroidUri(uri, requireContext())

        val preferences = requireActivity().getSharedPreferences(NAME_SHARED_PREFERENCES, MODE_PRIVATE)
        val serverAddress = preferences.getString(SERVER_ADDRESS, "10.0.2.2")

        //Upload image before
        val restApi = RestApiUtils(requireContext(), "https://$serverAddress:8443", null)
        restApi.uploadImage(file, object : RestApiUtils.ResponseCallback<String> {
            override fun result(response: Response<String>) {
                if (response.isResponseSuccessful()) {
                    contact.imageProfile = response.data!!
                    registerContact(contact)
                } else
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(),"Something went wrong!", Toast.LENGTH_LONG).show() }
            }
        })
    }

    private fun registerContact(contact: Contact) {
        val preferences = requireActivity().getSharedPreferences(NAME_SHARED_PREFERENCES, MODE_PRIVATE)
        val serverAddress = preferences.getString(SERVER_ADDRESS, "10.0.2.2")
        val userId = preferences.getString(CURRENT_USER, "-1")
        val jwtToken = preferences.getString(JWT_TOKEN, "null")

        //Register contact
        val restApi = RestApiUtils(requireContext(), "https://$serverAddress:8443", jwtToken)
        restApi.registerContact(userId!!.toInt(), contact, object : RestApiUtils.ResponseCallback<Contact> {
            override fun result(response: Response<Contact>) {
                if (response.isResponseSuccessful()) {
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(),"Successful registration", Toast.LENGTH_LONG).show()
                        requireActivity().onBackPressed()
                    }
                } else
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(),"Something went wrong!", Toast.LENGTH_LONG).show() }
            }
        })
    }
}