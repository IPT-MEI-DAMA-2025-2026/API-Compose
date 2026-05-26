package pt.ipt.dama2026.apicompose.retrofit

import pt.ipt.dama2026.apicompose.retrofit.service.NoteService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * instancia SINGLETON
 * que vai estabelecer o contacto com a API *
 */
object RetrofitInstance {
    private const val BASE_URL = "https://adamastor.ipt.pt/api/"

    val api: NoteService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NoteService::class.java)
    }
}