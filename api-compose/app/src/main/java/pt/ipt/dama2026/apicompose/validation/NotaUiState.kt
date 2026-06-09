package pt.ipt.dama2026.apicompose.validation


/**
 * Esta classe mantém o 'estado' dos objetos da interface
 */
data class NotaUiState(
    val nome: String = "",
    val nomeErro: String? = null,

    val descricao: String = "",

    val foto: String = ""
)