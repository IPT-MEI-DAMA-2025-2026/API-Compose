package pt.ipt.dama2026.apicompose


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import dagger.hilt.android.AndroidEntryPoint
import pt.ipt.dama2026.apicompose.ui.theme.APIComposeTheme
import pt.ipt.dama2026.apicompose.viewmodel.NotaViewModel
import pt.ipt.dama2026.apicompose.viewmodel.UiEvent

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            APIComposeTheme {

                // var auxiliar para a gestão do estado do snackbar
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 40.dp),
                    snackbarHost = {
                        SnackbarHost(
                            hostState = snackbarHostState,
                            snackbar = { snackbarData ->
                                Snackbar(
                                    snackbarData = snackbarData,
                                    containerColor = Color.Red, //Color(0xFF4CAF50),
                                    contentColor = Color.White
                                )
                            }
                        )
                    }
                ) { innerPadding ->
                    NotasScreen(
                        modifier = Modifier.padding(innerPadding),
                        snackbarHostState
                        )
                }
            }
        }
    }
}

@Composable
fun NotasScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    vm: NotaViewModel = hiltViewModel()
) {

    val listaNotas by vm.notas.collectAsState()
    val state by vm.uiState.collectAsState()


//    var nome by remember { mutableStateOf("") }
//    var descricao by remember { mutableStateOf("") }
//    var foto by remember { mutableStateOf("") }
//
//    // var auxiliar para a 'gestão de erros' do parâmetro "nome"
//    var nomeErro by remember { mutableStateOf<String?>(null) }


    val keyboardController = LocalSoftwareKeyboardController.current


    // gestor do evento de guardar uma Nota
    LaunchedEffect(Unit) {
        vm.events.collect { event ->
            when(event) {
                is UiEvent.ShowMessage -> {
                    snackbarHostState.showSnackbar(
                        event.message
                    )
                }
            }
        }
    }



    Column(modifier = modifier.fillMaxSize()) {
        // FORMULÁRIO
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = state.nome,
                onValueChange = vm::onNomeChanged,
                label = { Text("Nome") },
                isError = state.nomeErro != null
            )
            AnimatedVisibility(
                visible = state.nomeErro != null
            ) {
                Text(
                    text = state.nomeErro ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            OutlinedTextField(
                value = state.descricao,
                onValueChange = vm::onDescicaoChanged,
                label = { Text("Descrição") }
            )
            OutlinedTextField(
                value = state.foto,
                onValueChange = vm::onFotoChanged,
                label = { Text("URL Foto") }
            )
            Button(
                onClick = {
//                    Aqui já não há necessidade destas variáveis para validar os dados
//                    obtidos do formulário, porque esse trabalho passou para a VM
//
//                    // var. auxiliar para avaliar se há problemas com a adição do formulário
//                    var valido = true
//
//                    // avaliar o 'nome'
//                    if (nome.isBlank()) {
//                        valido = false
//                        nomeErro = "o campo NOME é de preenchimento obrigatório"
//                    }

//                    if (valido) {
//                        vm.addNota(nome, descricao, "noImage.jpg")
                    vm.addNota()

                    // fechar teclado
                    keyboardController?.hide()
//                        esta tarefa foi também transferida para o VM
//                        // limpar campos
//                        nome = ""
//                        descricao = ""
//                        foto = ""
//                    }
                }
            ) { Text("Adicionar Nota") }
        }





        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(
                items = listaNotas,
                key = { it.id }
            ) { nota ->
                Card {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = nota.title,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = nota.description)

                        Spacer(modifier = Modifier.height(12.dp))

                        AsyncImage(
                            model = "https://adamastor.ipt.pt/api/imagens/" + nota.image,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            /*
            O Compose:
                - recebe a lista
                - cria scroll automático
                - só renderiza itens visíveis
                - atualiza automaticamente quando o StateFlow muda
             */
        }
    }
}

/*
@Composable
fun NotaScreen(
    modifier: Modifier = Modifier,
    vm: NotaViewModel = viewModel()
) {
    val nota by vm.nota.collectAsState()

    nota?.let {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                text = it.title,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = it.description)

            Spacer(modifier = Modifier.height(16.dp))

            AsyncImage(
                model = "https://adamastor.ipt.pt/api/imagens/" + it.image,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
        }

    } ?: run {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
    }
}
 */