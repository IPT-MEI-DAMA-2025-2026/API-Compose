package pt.ipt.dama2026.apicompose.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pt.ipt.dama2026.apicompose.model.Note
import pt.ipt.dama2026.apicompose.retrofit.RetrofitInstance

class NotaViewModel : ViewModel() {

    private val _nota = MutableStateFlow<Note?>(null)
    val nota: StateFlow<Note?> = _nota

    // Construtor
    init {
        carregarNota()
    }

    /**
     * lê os dados de uma nota através dos dados de uma API
     */
    private fun carregarNota() {
        viewModelScope.launch {
            try {
                _nota.value = RetrofitInstance.api.getNote()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
