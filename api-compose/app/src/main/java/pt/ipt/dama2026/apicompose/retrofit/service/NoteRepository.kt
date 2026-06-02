package pt.ipt.dama2026.apicompose.retrofit.service

import javax.inject.Inject
import pt.ipt.dama2026.apicompose.model.Note
import pt.ipt.dama2026.apicompose.model.NoteRequest

class NoteRepository @Inject constructor(
    private val api: NoteService
) {

    suspend fun getNotas(): List<Note> {
        return api.obterNotas()
    }

    suspend fun createNota(request: NoteRequest): Note {
        return api.criarNota(request)
    }
}