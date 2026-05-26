package pt.ipt.dama2026.apicompose.model

import com.google.gson.annotations.SerializedName

data class Note(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image: String
)
