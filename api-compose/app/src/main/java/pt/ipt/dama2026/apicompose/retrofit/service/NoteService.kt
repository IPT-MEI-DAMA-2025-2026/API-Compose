package pt.ipt.dama2026.apicompose.retrofit.service

import pt.ipt.dama2026.apicompose.model.Note
import retrofit2.http.GET

/**
 * classe para interagir com a API
 */
interface NoteService {

//    /**
//     * ler os dados de uma Nota
//     */
//    @GET("api/Notes/1")
//    suspend fun getNote(): Note

    /**
     * obter uma lista de notas, da API
     */
    @GET("api/Notes")
    suspend fun obterNotas(): List<Note>

}