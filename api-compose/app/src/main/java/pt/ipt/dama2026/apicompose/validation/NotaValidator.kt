package pt.ipt.dama2026.apicompose.validation

import jakarta.inject.Inject

/**
 * Esta classe contém as 'regras do negócio'
 * Apenas sabe avaliar a qualidade dos dados introduzidos
 * pelo utilizador.
 */
class NotaValidator @Inject constructor() {

    fun validarNome(nome: String): ValidationResult {
        return if (nome.isBlank()) {
            ValidationResult(false, "O nome é obrigatório")
        } else {
            ValidationResult(true)
        }
    }
}

// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// NOTA: NÃO ESQUECER que as mensagens de texto devem ser adicionadas
//       no ficheiro de strings, e depois aqui referenciadas
// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++