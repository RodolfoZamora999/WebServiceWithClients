package com.rodolfozamora.persistence

import android.content.Context
import android.net.Uri
import org.apache.commons.io.FileUtils
import java.io.File

/**
 * Converts a android uri to a java file object
 */
fun createFileFromAndroidUri(uri: Uri, context: Context): File {
    val resolver = context.contentResolver
    val type = resolver.getType(uri)!!.split("/")[1]

    val temFile = File(context.cacheDir, "image_temp.$type")
    val input = resolver.openInputStream(uri)

    FileUtils.copyInputStreamToFile(input, temFile)

    return temFile
}