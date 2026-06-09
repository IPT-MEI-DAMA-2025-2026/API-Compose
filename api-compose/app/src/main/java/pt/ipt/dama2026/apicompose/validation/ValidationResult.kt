package pt.ipt.dama2026.apicompose.validation

/**
 * Devolve o resultado da validação
 * de um atributo
 * - true: se há sucesso
 * - false: se não há sucesso
 *          neste caso, devolve uma mensagem de erro
 */
data class ValidationResult(
    val sucesso: Boolean,
    val erro: String? = null
)
