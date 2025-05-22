package ipca.aulas.gappacademy.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ipca.aulas.gappacademy.R
import ipca.aulas.gappacademy.components.LoginTextField
import ipca.aulas.gappacademy.AuthViewModel
import ipca.aulas.gappacademy.ui.theme.GappGreen

@Composable
fun LoginView(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    onLoginSuccess: () -> Unit
) {
    val loginViewModel: LoginViewModel = viewModel()
    val loginState by loginViewModel.state.collectAsState()
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()

    LaunchedEffect(loginState.userId) {
        loginState.userId?.let { uid ->
            println("Login bem-sucedido, UID: $uid")
            authViewModel.login(uid)
            onLoginSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GappGreen)
            .imePadding()
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(GappGreen),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Bem-vindo",
                color = Color.White,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logoipca2),
                    contentDescription = "IPCA Logo",
                    contentScale = ContentScale.Fit
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoginTextField(
                    value = loginState.email,
                    onValueChange = loginViewModel::onEmailChange,
                    placeholder = "E-mail"
                )

                Spacer(modifier = Modifier.height(16.dp))

                LoginTextField(
                    value = loginState.password,
                    onValueChange = loginViewModel::onPasswordChange,
                    placeholder = "Palavra-Passe",
                    isPassword = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { loginViewModel.login() },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = GappGreen
                    ),
                    elevation = ButtonDefaults.buttonElevation(6.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .padding(horizontal = 72.dp)
                ) {
                    if (loginState.isLoading) {
                        CircularProgressIndicator(
                            color = Color(0xFF264D3D),
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text("Entrar", color = GappGreen, fontSize = 16.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                loginState.error?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}