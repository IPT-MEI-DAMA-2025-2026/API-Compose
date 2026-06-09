package pt.ipt.dama2026.apicompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pt.ipt.dama2026.apicompose.model.Note
import pt.ipt.dama2026.apicompose.model.NoteRequest
import pt.ipt.dama2026.apicompose.retrofit.service.NoteRepository
import pt.ipt.dama2026.apicompose.validation.NotaUiState
import pt.ipt.dama2026.apicompose.validation.NotaValidator
import javax.inject.Inject

@HiltViewModel
class NotaViewModel @Inject constructor(
    private val repository: NoteRepository,
    private val validator: NotaValidator
) : ViewModel() {

    private val _notas = MutableStateFlow<List<Note>>(emptyList())
    // atributo a ser consumido pela interface
    val notas: StateFlow<List<Note>> = _notas

    private val _uiState = MutableStateFlow(NotaUiState())
    // atributo a ser consumido pela interface
    val uiState: StateFlow<NotaUiState> = _uiState

    init {
        loadNotas()
    }

    /**
     * Lê as 'notas' disponibilizadas pela API
     */
    fun loadNotas() {
        viewModelScope.launch {
            _notas.value = repository.getNotas().sortedByDescending { it.id }
        }
    }


//    fun addNota(nome: String, descricao: String, foto: String) {
//        viewModelScope.launch {
//            val novaNota = repository.createNota(
//                NoteRequest(nome, descricao, foto)
//            )
//            _notas.value = _notas.value + novaNota
//        }
//    }

    /**
     * Adiciona uma 'nota' na API
     */
    fun addNota() {

        // obter o estado atual da interface
        // os dados que nos interessa aqui consultar
        val state = _uiState.value

        // outras validações poderiam aqui ser adicionadas...
        val nomeResult = validator.validarNome(state.nome)

        // avalia se há sucesso na validação do 'nome'
        if (!nomeResult.sucesso) {
            _uiState.update {
                it.copy(
                    // o valor aqui adicionado é o valor devolvido
                    // pelo validador do 'nome'
                    // ele é que sabe as regras de validação e o
                    // motivo da não aceitação do valor
                    nomeErro = nomeResult.erro
                )
            }
            return
        }

        viewModelScope.launch {
            val novaNota = repository.createNota(
                NoteRequest(
                    state.nome,
                    state.descricao,
                    "noImage.jpg"   //state.foto
                )
            )
            _notas.value = _notas.value + novaNota

            // limpar o estado das variáveis da interface
            _uiState.update {
                it.copy(
                    nome = "",
                    descricao = "",
                    foto = "",
                    nomeErro = null
                )
            }
        }
    }

    /**
     * Esta função é chamada sempre que o utilizador
     * altera o valor do campo 'nome'
     * @param valor: o novo valor do campo 'nome'
     */
    fun onNomeChanged(valor: String) {
        _uiState.update {
            it.copy(
                nome = valor,
                nomeErro = null
            )
        }
    }

    /**
     * Esta função é chamada sempre que o utilizador
     * altera o valor do campo 'descrição'
     * @param valor: o novo valor do campo 'descrição'
     */
    fun onDescicaoChanged(valor: String) {
        _uiState.update {
            it.copy(
                descricao = valor
            )
        }
    }

    /**
     * Esta função é chamada sempre que o utilizador
     * altera o valor do campo 'foto'
     * @param valor: o novo valor do campo 'foto'
     */
    fun onFotoChanged(valor: String) {
        _uiState.update {
            it.copy(
                foto = valor
            )
        }
    }

}