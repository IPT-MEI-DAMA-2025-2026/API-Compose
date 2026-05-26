package pt.ipt.dama2026.apicompose.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pt.ipt.dama2026.apicompose.model.Note
import pt.ipt.dama2026.apicompose.model.NoteRequest
import pt.ipt.dama2026.apicompose.retrofit.RetrofitInstance

class NotaViewModel : ViewModel() {

    private val _notas = MutableStateFlow<List<Note?>>(emptyList())
    val notas: StateFlow<List<Note?>> = _notas

    // Construtor
    init {
        carregarNotas()
    }

    /**
     * lê os dados das notas disponíveis através da API
     */
    private fun carregarNotas() {
        viewModelScope.launch {
            try {
                _notas.value = RetrofitInstance.api.obterNotas().sortedByDescending { it.id }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun adicionarNota(titulo: String, descricao: String, imagem: String) {
        viewModelScope.launch {
            try {
                val novaNota = RetrofitInstance.api.criarNota(
                    NoteRequest(titulo, descricao, imagem)
                )
                // actualizar a lista de notas no ecrã
                _notas.value += novaNota
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
