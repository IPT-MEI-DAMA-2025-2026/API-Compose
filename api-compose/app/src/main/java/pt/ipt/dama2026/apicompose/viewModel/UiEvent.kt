package pt.ipt.dama2026.apicompose.viewmodel

/**
 * 'transportador' dos eventos para a interface
 */
sealed class UiEvent {
    data class ShowMessage(
        val message: String
    ) : UiEvent()
}
