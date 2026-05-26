package pt.ipt.dama2026.apicompose.model

import com.google.gson.annotations.SerializedName

/**
 * objeto que será enviado para a API
 */
data class NoteRequest(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image: String

)
