package com.rodolfozamora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.rodolfozamora.tls.createOkHttpSecureBuilder
import okhttp3.Request

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            loadSSL()
        }
    }

    private fun loadSSL() {
        //val url = "https://www.webservice.com:8443/api/user" Todo: Solve this
        val url = "https://10.0.2.2:8443/api/user" //Android virtual device network

        val okHttpClient = createOkHttpSecureBuilder(this).build()
        val request = Request.Builder().get().url(url).build()

        Thread {
            val response = okHttpClient.newCall(request).execute()
            Log.d("HTTP", "Protocol: " + response.protocol.toString())
            Log.d("HTTP", "Status: " + response.code)

            response.body?.let {
                val message = it.string()
                Log.d("HTTP", message)

                runOnUiThread {
                    val txtView = findViewById<TextView>(R.id.textView)
                    txtView.text = message
                }
            }
        }.start()
    }
}