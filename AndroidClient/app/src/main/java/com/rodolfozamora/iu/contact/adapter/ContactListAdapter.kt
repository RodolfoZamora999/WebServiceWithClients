package com.rodolfozamora.iu.contact.adapter

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.rodolfozamora.data.model.Contact

import com.rodolfozamora.R;
import com.rodolfozamora.network.api.Response
import com.rodolfozamora.network.api.RestApiUtils
import com.rodolfozamora.persistence.JWT_TOKEN
import com.rodolfozamora.persistence.NAME_SHARED_PREFERENCES
import com.rodolfozamora.persistence.SERVER_ADDRESS

class ContactListAdapter (context: Context, resource: Int) : ArrayAdapter<Contact>(context, resource) {
    private val restApi: RestApiUtils

    init {
        val preferences = context.getSharedPreferences(NAME_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val baseUrl = preferences.getString(SERVER_ADDRESS, "10.0.2.2")
        val url = "https://$baseUrl:8443"
        val jwtToken = preferences.getString(JWT_TOKEN, "null")
        restApi = RestApiUtils(context, url, jwtToken)
    }


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

        val imgProfile = view?.findViewById<ImageView>(R.id.imgProfileContactItem)
        if (!contact!!.imageProfile.isNullOrEmpty()) {
            loadImage(contact.imageProfile!!, imgProfile!!)
        }

        return view!!
    }

    private fun loadImage(relativePathImage: String, imageView: ImageView) {
        restApi.downloadImage(relativePathImage, object : RestApiUtils.ResponseCallback<Bitmap> {
            override fun result(response: Response<Bitmap>) {
                if (response.isResponseSuccessful()) {
                    imageView.post {
                        imageView.setImageBitmap(response.data)
                        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                    }
                }
                else {
                    imageView.post {
                        Toast.makeText(imageView.context, "Something went wrong!", Toast.LENGTH_LONG).show()
                    }
                }
            }

        })
    }

}