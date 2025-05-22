package ipca.aulas.gappacademy.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ipca.aulas.gappacademy.ui.theme.StyledDropdownMenu
import ipca.aulas.gappacademy.AuthViewModel

@Composable
fun Header(
    navController: NavHostController,
    authViewModel: AuthViewModel,
) {
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()
    val userName by authViewModel.userName.collectAsState()

    var menuExpanded by remember { mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val headerTitle = when {
        currentRoute == "cursos" -> "CURSOS"
        currentRoute?.startsWith("ucview") == true -> "UCS"
        currentRoute?.startsWith("ucdetail") == true -> "DETALHES"
        currentRoute == "instalacoes" -> "INSTALAÇÕES"
        currentRoute == "sobrenos" -> "SOBRE NÓS"
        currentRoute == "oportunidades" -> "OPORTUNIDADES"
        currentRoute == "login" -> "LOGIN"
        currentRoute == "perfil" -> "PERFIL"
        else -> "IPCA"
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            currentRoute == "home" -> {
                if (isLoggedIn) {
                    val firstName = userName?.split(" ")?.firstOrNull() ?: "Usuário"
                    Text(
                        text = "Olá, $firstName",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp)
                            .clickable {
                                navController.navigate("perfil")
                            }
                    )
                } else {
                    Text(
                        text = "Login",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp)
                            .clickable {
                                navController.navigate("login")
                            }
                    )
                }
            }

            currentRoute == "perfil" -> {
                var isLoggingOut by remember { mutableStateOf(false) }

                IconButton(
                    onClick = {
                        if (!isLoggingOut) {
                            isLoggingOut = true
                            authViewModel.logout()
                        }
                    },
                    enabled = !isLoggingOut,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    if (isLoggingOut) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Logout",
                            tint = Color.White
                        )
                    }
                }

                if (isLoggingOut) {
                    LaunchedEffect(Unit) {
                        kotlinx.coroutines.delay(1000)
                        navController.navigate("home") {
                            popUpTo("home") { inclusive = true }
                        }
                        isLoggingOut = false
                    }
                }
            }

            else -> {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.White
                    )
                }
            }
        }

        Text(
            text = headerTitle,
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Center)
                .clickable {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                }
        )

        Box(
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            val rotationAngle by animateFloatAsState(
                targetValue = if (menuExpanded) 90f else 0f, label = "menuRotation"
            )

            IconButton(
                onClick = { menuExpanded = !menuExpanded }
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu",
                    tint = Color.White,
                    modifier = Modifier.rotate(rotationAngle)
                )
            }
            StyledDropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false },
                onItemClick = { route ->
                    menuExpanded = false
                    navController.navigate(route)
                },
                isLoggedIn = isLoggedIn
            )
        }
    }
}