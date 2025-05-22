package ipca.aulas.gappacademy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ipca.aulas.gappacademy.components.Header
import ipca.aulas.gappacademy.ui.article.ArticleDetailView
import ipca.aulas.gappacademy.ui.cursos.CursosView
import ipca.aulas.gappacademy.ui.cursos.ucs.ucdetail.UcDetailView
import ipca.aulas.gappacademy.ui.homescreen.HomeView
import ipca.aulas.gappacademy.ui.homescreen.articles.articleDetail.ArticleDetailViewModel
import ipca.aulas.gappacademy.ui.instalacoes.InstalacoesView
import ipca.aulas.gappacademy.ui.login.LoginView
import ipca.aulas.gappacademy.ui.oportunidades.OportunidadesView
import ipca.aulas.gappacademy.ui.perfil.PerfilView
import ipca.aulas.gappacademy.ui.perfil.PerfilViewModel
import ipca.aulas.gappacademy.ui.sobrenos.SobreNosView
import ipca.aulas.gappacademy.ui.theme.GappAcademyTheme
import ipca.aulas.gappacademy.ui.theme.GappGreen
import ipca.aulas.gappacademy.ui.uc.UcView
import androidx.lifecycle.viewmodel.compose.viewModel as viewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GappAcademyTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = GappGreen,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column {
                Header(
                    navController = navController,
                    authViewModel = authViewModel,
                )
                NavHost(
                    navController = navController,
                    startDestination = "home",
                    modifier = Modifier
                        .weight(1f)
                ) {
                    composable("home") {
                        HomeView(navController = navController)
                    }
                    composable("sobrenos") {
                        SobreNosView()
                    }
                    composable("instalacoes") {
                        InstalacoesView()
                    }
                    composable("cursos") {
                        CursosView(navController = navController)
                    }
                    composable("ucview/{siglaCurso}") {
                        val siglaCurso = it.arguments?.getString("siglaCurso")
                        UcView(
                            siglaCurso = siglaCurso!!,
                            onUcClick = { uc ->
                                navController.navigate("ucdetail/${uc.docId}")
                            },
                        )
                    }
                    composable("ucdetail/{ucId}") {
                        val ucId = it.arguments?.getString("ucId")
                        UcDetailView(
                            ucId = ucId!!,
                            viewModel = viewModel(),
                        )
                    }
                    composable("login") {
                        LoginView(
                            authViewModel = authViewModel,
                            onLoginSuccess = {
                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }
                    composable("oportunidades") {
                        OportunidadesView()
                    }
                    composable("articleDetail/{articleId}") { backStackEntry ->
                        val articleId =
                            backStackEntry.arguments?.getString("articleId")?.toIntOrNull()
                        val viewModel: ArticleDetailViewModel = viewModel()
                        val article = articleId?.let { viewModel.getArticleById(it) }

                        if (article != null) {
                            ArticleDetailView(navController = navController, article = article)
                        } else {
                            Text("Artigo não encontrado")
                        }
                    }
                    composable("perfil") {
                        val userId by authViewModel.userId.collectAsState()
                        val userName by authViewModel.userName.collectAsState()

                        if (userId.isNullOrEmpty()) {
                            LaunchedEffect(Unit) {
                                navController.navigate("perfil") {
                                    popUpTo("perfil") { inclusive = true }
                                }
                            }
                        } else {
                            val perfilViewModel: PerfilViewModel = viewModel()
                            PerfilView(
                                userId = userId!!,
                                userName = userName ?: "Usuário",
                                perfilViewModel = perfilViewModel,
                            )
                        }
                    }
                }
            }
        }
    }
}