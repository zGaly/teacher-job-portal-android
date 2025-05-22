package ipca.aulas.gappacademy.ui.sobrenos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ipca.aulas.gappacademy.ui.theme.GappGreen

@Composable
fun SobreNosView(
    viewModel: SobreNosViewModel = viewModel(),
) {
    val state by viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GappGreen)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    painter = painterResource(id = state.logoGapp),
                    contentDescription = "Logo Gapp",
                    modifier = Modifier
                        .size(250.dp)
                        .padding(top = state.paddingTopLogo.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        EquipaMembroCircle(state.equipa[0], getMembroImage(0))
                        EquipaMembroCircle(state.equipa[1], getMembroImage(1))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    EquipaMembroCircle(state.equipa[2], getMembroImage(2))
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = GappGreen)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = state.descricao,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Image(
                        painter = painterResource(id = state.logoAcademy),
                        contentDescription = "Logo Gapp Academy",
                        modifier = Modifier.size(70.dp)
                    )
                }
            }
        }
    }
}

