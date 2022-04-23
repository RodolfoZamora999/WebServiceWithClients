package com.rodolfozamora.network

import android.content.Context
import com.rodolfozamora.R
import okhttp3.OkHttpClient
import java.io.BufferedInputStream
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

fun createOkHttpSecureBuilder(context: Context): OkHttpClient.Builder {
    val cf: CertificateFactory = CertificateFactory.getInstance("X.509")
    val caInput: InputStream =
        BufferedInputStream(context.resources.openRawResource(R.raw.my_projects_ca))
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

    return OkHttpClient.Builder()
        .sslSocketFactory(sslContext.socketFactory, tmf.trustManagers[0] as X509TrustManager)
}