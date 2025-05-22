package ipca.aulas.gappacademy.ui.article

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import ipca.aulas.gappacademy.models.Article
import ipca.aulas.gappacademy.ui.theme.GappGreen

@Composable
fun ArticleDetailView(
    navController: NavHostController,
    article: Article,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GappGreen)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(8.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = getDrawableId(article.imageName)),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Text(
                    text = article.title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 20.sp,
                        color = Color.White
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .background(
                            Color(0x99000000),
                            shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                        )
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth() 
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Descrição",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 18.sp,
                            color = Color(0xFF0F4F33)
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = 1.dp,
                        color = Color(0xFFDDDDDD)
                    )

                    Text(
                        text = article.description,
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
                        color = Color.Black,
                        textAlign = TextAlign.Justify,
                    )
                }
            }
        }

        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://ipca.pt/"))
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = GappGreen,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(top = 16.dp, bottom = 8.dp)
                .size(width = 250.dp, height = 45.dp)
                .height(48.dp)
                .border(2.dp, Color.White, shape = RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "Visitar site do IPCA",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Color.White
            )
        }
    }
}

fun getDrawableId(imageName: String): Int {
    return try {
        val resourceId = imageName.toInt()
        if (resourceId != 0) resourceId else ipca.aulas.gappacademy.R.drawable.ipca_article
    } catch (e: NumberFormatException) {
        ipca.aulas.gappacademy.R.drawable.ipca_article
    }
}