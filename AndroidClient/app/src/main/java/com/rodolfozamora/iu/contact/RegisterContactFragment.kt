package com.rodolfozamora.iu.contact

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.rodolfozamora.R

class RegisterContactFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register_contact, container, false)
    }

    override fun onStart() {
        super.onStart()

        requireActivity().findViewById<ImageButton>(R.id.btnCancelContactRegister).apply {
            this.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }

        requireActivity().findViewById<ImageButton>(R.id.btnSaveContactRegister).apply {
            this.setOnClickListener {
                Toast.makeText(context, "Saved...", Toast.LENGTH_LONG).show()
            }
        }

        val btnImage = requireActivity().findViewById<ImageButton>(R.id.btnImageProfileContactRegister)
        btnImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
            }
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivityForResult(intent, 1)
            }
        }
    }

    private fun registerContact() {

    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                val uri = data?.data
                requireActivity().findViewById<ImageView>(R.id.imgProfileContactRegister).apply {
                    setImageURI(uri)
                    scaleType = ImageView.ScaleType.CENTER_CROP
                }
            }
        }
    }
}