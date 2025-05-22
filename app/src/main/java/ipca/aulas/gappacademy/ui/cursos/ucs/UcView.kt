package ipca.aulas.gappacademy.ui.uc

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
import ipca.aulas.gappacademy.components.SearchBar
import ipca.aulas.gappacademy.models.UCs
import ipca.aulas.gappacademy.ui.theme.GappGreen

@Composable
fun UcView(
    siglaCurso: String,
    onUcClick: (UCs) -> Unit,
) {
    val viewModel: UcViewModel = viewModel()
    val state by viewModel.state.collectAsState()
    val filteredUcs by viewModel.filteredUcs.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadUcs(siglaCurso)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GappGreen)
            .padding(16.dp)
    ) {
        SearchBar(
            searchTextState = viewModel.searchText.collectAsState(),
            onSearchTextChange = { viewModel.updateSearchText(it) },
            placeholder = "Pesquisar UCs..."
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (state.isLoading) {
            Text(
                text = "Carregando...",
                color = Color.White,
                modifier = Modifier.fillMaxSize()
            )
        } else if (state.error != null) {
            Text(
                text = "Erro: ${state.error}",
                color = Color.Red,
                modifier = Modifier.fillMaxSize()
            )
        } else if (filteredUcs.isEmpty()) {
            Text(
                text = "Não foram encontradas UCs.",
                color = Color.White,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredUcs) { uc ->
                    UcRowView(
                        uc = uc,
                        onClick = { onUcClick(uc) }
                    )
                }
            }
        }
    }
}