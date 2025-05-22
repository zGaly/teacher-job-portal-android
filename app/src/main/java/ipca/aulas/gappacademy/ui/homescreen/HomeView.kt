package ipca.aulas.gappacademy.ui.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ipca.aulas.gappacademy.ui.homescreen.articles.ArticleRowView
import ipca.aulas.gappacademy.ui.theme.GappGreen

@Composable
fun HomeView(
    navController: NavHostController,
    viewModel: HomeViewModel = viewModel(),
) {
    val articles by remember { viewModel.articles }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GappGreen)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(articles) { article ->
                ArticleRowView(
                    article = article,
                    onClick = {
                        navController.navigate("articleDetail/${article.articleId}")

                    },
                )
            }
        }
    }
}