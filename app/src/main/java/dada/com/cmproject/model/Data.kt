package dada.com.cmproject.model

import java.io.Serializable

data class Data(
    val albumId:Int,
    val id:Int,
    val title:String,
    val url:String,
    val thumbnailUrl:String
):Serializable