package com.rodolfozamora.iu.config

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup

import com.rodolfozamora.R
import com.rodolfozamora.persistence.NAME_SHARED_PREFERENCES
import com.rodolfozamora.persistence.SERVER_ADDRESS

class ConfigActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        val preferences = getSharedPreferences(NAME_SHARED_PREFERENCES, MODE_PRIVATE)

        val radioAddress = findViewById<RadioGroup>(R.id.radioGroupAddress)
        val radioIp = findViewById<RadioButton>(R.id.radioAndroidToLocalhost)
        val radioDNS = findViewById<RadioButton>(R.id.radioDNSName)

        when(preferences.getString(SERVER_ADDRESS, "10.0.2.2")) {
            "10.0.2.2" -> radioIp.isChecked = true
            "www.webservice.com" -> radioDNS.isChecked = true
        }

        radioAddress.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                radioIp.id -> preferences.edit().putString(SERVER_ADDRESS, "10.0.2.2").apply()
                radioDNS.id -> preferences.edit().putString(SERVER_ADDRESS, "www.webservice.com").apply()
            }
        }
    }
}