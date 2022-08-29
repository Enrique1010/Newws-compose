package com.erapps.newws_compose.data.models

/*import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey*/
import com.erapps.newws.data.models.Source
import com.erapps.newws_compose.utils.Exclude
import com.google.gson.annotations.SerializedName
import java.io.Serializable

//@Entity(tableName = "articles")
data class Article (
    @Exclude(deserialize = false)
    //@PrimaryKey(autoGenerate = true)
    //@ColumnInfo(name = "id")
    val id: Int,
    //@ColumnInfo(name = "source")
    @SerializedName("source")
    val source      : Source? = null,
    //@ColumnInfo(name = "author")
    @SerializedName("author")
    val author      : String? = null,
    //@ColumnInfo(name = "title")
    @SerializedName("title")
    val title       : String? = null,
    //@ColumnInfo(name = "description")
    @SerializedName("description")
    val description : String? = null,
    //@ColumnInfo(name = "url")
    @SerializedName("url")
    val url         : String? = null,
    //@ColumnInfo(name = "urlToImage")
    @SerializedName("urlToImage")
    val urlToImage  : String? = null,
    //@ColumnInfo(name = "publishedAt")
    @SerializedName("publishedAt")
    val publishedAt : String? = null,
    //@ColumnInfo(name = "content")
    @SerializedName("content")
    val content     : String? = null
): Serializable
