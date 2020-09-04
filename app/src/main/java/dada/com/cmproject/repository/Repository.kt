package dada.com.cmproject.repository

import dada.com.cmproject.const.ApiConst.Companion.BASE_URL
import dada.com.cmproject.const.ApiConst.Companion.DEFAULT_TIMEOUT
import dada.com.cmproject.const.ApiConst.Companion.GET
import dada.com.cmproject.model.Data
import dada.com.cmproject.util.readStream
import org.json.JSONArray
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL

class Repository {


    suspend fun fetchData(): List<Data> {
        val url = URL(BASE_URL)
        val connection = (url.openConnection() as HttpURLConnection).apply {
            setRequestProperty ("Connection", "close")
            requestMethod = GET
            readTimeout = DEFAULT_TIMEOUT
            doInput = true
            connect()
        }

        val stream = BufferedInputStream(connection.inputStream)
        val result = readStream(inputStream = stream)
        val list = mutableListOf<Data>()
        val jsonArray =  JSONArray(result)
        for (i in 0 until jsonArray.length()){
            val jsonObject = jsonArray.getJSONObject(i)
            list.add(
                Data(
                    albumId = jsonObject.getInt("albumId"),
                    id = jsonObject.getInt("id"),
                    title = jsonObject.getString("title"),
                    url = jsonObject.getString("url"),
                    thumbnailUrl = jsonObject.getString("thumbnailUrl")
                )
            )
        }

        return list
    }




}