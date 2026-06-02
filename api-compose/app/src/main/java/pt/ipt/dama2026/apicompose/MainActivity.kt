package pt.ipt.dama2026.apicompose


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import dagger.hilt.android.AndroidEntryPoint
import pt.ipt.dama2026.apicompose.ui.theme.APIComposeTheme
import pt.ipt.dama2026.apicompose.viewmodel.NotaViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            APIComposeTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 40.dp)
                ) { innerPadding ->
                    NotasScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun NotasScreen(
    modifier: Modifier = Modifier,
    vm: NotaViewModel = hiltViewModel()
) {

    val listaNotas by vm.notas.collectAsState()

    var nome by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var foto by remember { mutableStateOf("") }

    // var auxiliar para a 'gestão de erros' do parâmetro "nome"
    var nomeErro by remember { mutableStateOf<String?>(null) }


    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = modifier.fillMaxSize()) {
        // FORMULÁRIO
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = nome,
                onValueChange = {
                    nome = it
                    nomeErro = null
                },
                label = { Text("Nome") },
                isError = nomeErro != null,
                supportingText = { nomeErro?.let { Text(it) } }
            )
            OutlinedTextField(
                value = descricao,
                onValueChange = { descricao = it },
                label = { Text("Descrição") }
            )
            OutlinedTextField(
                value = foto,
                onValueChange = { foto = "noImage.jpg" },
                label = { Text("URL Foto") }
            )
            Button(
                onClick = {
                    // var. auxiliar para avaliar se há problemas com a adição do formulário
                    var valido = true

                    // avaliar o 'nome'
                    if (nome.isBlank()) {
                        valido = false
                        nomeErro = "o campo NOME é de preenchimento obrigatório"
                    }

                    if (valido) {
                        vm.addNota(nome, descricao, foto)

                        // fechar teclado
                        keyboardController?.hide()
                        // limpar campos
                        nome = ""
                        descricao = ""
                        foto = ""
                    }
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