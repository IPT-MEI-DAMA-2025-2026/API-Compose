package pt.ipt.dama2026.apicompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import pt.ipt.dama2026.apicompose.ui.theme.APIComposeTheme
import pt.ipt.dama2026.apicompose.viewModel.NotaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            APIComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()
                    .padding(top = 40.dp)
                ) { innerPadding ->
                    NotaScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


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


//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    APIComposeTheme {
//        Greeting("Android")
//    }
//}