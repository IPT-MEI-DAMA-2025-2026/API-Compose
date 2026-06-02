package pt.ipt.dama2026.apicompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pt.ipt.dama2026.apicompose.model.Note
import pt.ipt.dama2026.apicompose.model.NoteRequest
import pt.ipt.dama2026.apicompose.retrofit.service.NoteRepository
import javax.inject.Inject

@HiltViewModel
class NotaViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _notas = MutableStateFlow<List<Note>>(emptyList())
    val notas: StateFlow<List<Note>> = _notas

    init {
        loadNotas()
    }

    fun loadNotas() {
        viewModelScope.launch {
            _notas.value = repository.getNotas().sortedByDescending { it.id }
        }
    }

    fun addNota(nome: String, descricao: String, foto: String) {
        viewModelScope.launch {
            val novaNota = repository.createNota(
                NoteRequest(nome, descricao, foto)
            )
            _notas.value = _notas.value + novaNota
        }
    }

}