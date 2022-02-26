package com.rodolfozamora

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedInputStream
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

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
        //val ssl = loadMyOwnSSLContext(this);
        val url = "https://www.webservice.com:8443/api/user"
        val sslC = loadOwnSSLContext(this)
        val trustManager = loadOwnTrustManager(this)
        val okHttpClient = OkHttpClient.Builder().sslSocketFactory(sslC.socketFactory, trustManager).build()
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

fun loadOwnSSLContext(context: Context): SSLContext {
    //Load Cert
    val cf: CertificateFactory = CertificateFactory.getInstance("X.509")
    val caInput: InputStream = BufferedInputStream(context.resources.openRawResource(R.raw.my_projects_ca))
    val ca: X509Certificate = caInput.use {
        cf.generateCertificate(it) as X509Certificate
    }

    //Create a keystore
    val keyStoreType = KeyStore.getDefaultType()
    val keyStore = KeyStore.getInstance(keyStoreType).apply {
        load(null, null)
        setCertificateEntry("ca", ca)
    }

    //Create a TrustManager
    val tmfAlgorithm: String = TrustManagerFactory.getDefaultAlgorithm()
    val tmf: TrustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm).apply {
        init(keyStore)
    }

    val sslContext: SSLContext = SSLContext.getInstance("TLS").apply {
        init(null, tmf.trustManagers, null)
    }

    caInput.close()
    return sslContext
}

fun loadOwnTrustManager(context: Context): X509TrustManager {
    //Load Cert
    val cf: CertificateFactory = CertificateFactory.getInstance("X.509")
    val caInput: InputStream = BufferedInputStream(context.resources.openRawResource(R.raw.my_projects_ca))
    val ca: X509Certificate = caInput.use {
        cf.generateCertificate(it) as X509Certificate
    }

    //Create a keystore
    val keyStoreType = KeyStore.getDefaultType()
    val keyStore = KeyStore.getInstance(keyStoreType).apply {
        load(null, null)
        setCertificateEntry("ca", ca)
    }

    //Create a TrustManager
    val tmfAlgorithm: String = TrustManagerFactory.getDefaultAlgorithm()
    val tmf: TrustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm).apply {
        init(keyStore)
    }

    caInput.close()

    return tmf.trustManagers[0] as X509TrustManager
}