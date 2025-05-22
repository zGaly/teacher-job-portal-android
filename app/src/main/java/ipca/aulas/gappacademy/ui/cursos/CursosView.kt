package ipca.aulas.gappacademy.ui.cursos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ipca.aulas.gappacademy.components.SearchBar
import ipca.aulas.gappacademy.ui.theme.GappGreen

@Composable
fun CursosView(
    navController: NavHostController,
) {
    val viewModel: CursosViewModel = viewModel()
    val filteredCursos by viewModel.filteredCursos.collectAsState()
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GappGreen)
            .padding(16.dp)
    ) {
        SearchBar(
            searchTextState = viewModel.searchText.collectAsState(),
            onSearchTextChange = { viewModel.updateSearchText(it) },
            placeholder = "Pesquisar cursos..."
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (state.isLoading) {
            Text(
                text = "Loading...",
                color = Color.White,
                modifier = Modifier.fillMaxSize()
            )
        } else if (state.error != null) {
            Text(
                text = "Error: ${state.error}",
                color = Color.Red,
                modifier = Modifier.fillMaxSize()
            )
        } else if (filteredCursos.isEmpty()) {
            Text(
                text = "Não foram encontrados cursos.",
                color = Color.White,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp)
            ) {
                items(filteredCursos) { curso ->
                    CursosRowView(
                        curso = curso,
                        onClick = {
                            navController.navigate("ucview/${curso.siglaCurso}")
                        }
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getAll()
    }
}