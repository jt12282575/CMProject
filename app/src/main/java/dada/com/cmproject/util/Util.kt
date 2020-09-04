package dada.com.cmproject.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import dada.com.cmproject.const.ApiConst.Companion.GET
import dada.com.cmproject.const.ViewConst.Companion.EACH_ROM
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection




fun readStream(inputStream: BufferedInputStream): String {
    val bufferedReader = BufferedReader(InputStreamReader(inputStream))
    val stringBuilder = StringBuilder()
    bufferedReader.forEachLine { stringBuilder.append(it) }
    bufferedReader.close()
    return stringBuilder.toString()
}

suspend fun loadImage(urlString:String, targetImageWidth:Int):Bitmap?{
    var mBitmap: Bitmap? = null
    try {
        val inputStream = openHttpConnection(urlString)
        val options = BitmapFactory.Options()
        val originalBitmap = BitmapFactory.decodeStream(inputStream, null, options)
        val imageWidth = options.outWidth
        val imageHeight = options.outHeight
        val racial:Float = (targetImageWidth.toFloat()/imageWidth.toFloat())
        val targetImageHeight = (imageHeight.toFloat()*racial).toInt()
        originalBitmap?.let {
            mBitmap = Bitmap.createScaledBitmap(originalBitmap,targetImageWidth,targetImageHeight,true)
        }


        inputStream?.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return mBitmap

}

@Throws(IOException::class)
fun openHttpConnection(strURL: String): InputStream? {
    var inputStream: InputStream? = null
    val url = URL(strURL)
    val conn: URLConnection = url.openConnection()
    try {
        val httpConn: HttpURLConnection = conn as HttpURLConnection
        httpConn.apply {
            requestMethod = GET
            setRequestProperty ("User-Agent", "Mozilla/5.0")
            connect()
        }
        if (httpConn.responseCode === HttpURLConnection.HTTP_OK) {
            inputStream = httpConn.inputStream
        }
    } catch (ex: Exception) {

    }
    return inputStream
}

fun getScreenWidth(windowManager: WindowManager):Int{
    val dm = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(dm)
    return dm.widthPixels
}