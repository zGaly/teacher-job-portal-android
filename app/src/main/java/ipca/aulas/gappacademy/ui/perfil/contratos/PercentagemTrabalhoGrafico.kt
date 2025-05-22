package ipca.aulas.gappacademy.ui.perfil.contratos

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ipca.aulas.gappacademy.models.ContratoDocenteDetalhado

@Composable
fun EvolucaoPagamentoGrafico(contratos: List<ContratoDocenteDetalhado>) {
    if (contratos.isEmpty()) return

    val sortedContratos = contratos.sortedBy { it.anoLetivo }
    val anos = sortedContratos.map { it.anoLetivo ?: "N/A" }
    val percentagens = sortedContratos.map { it.percentagem?.replace("%", "")?.toFloatOrNull() ?: 0f }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Evolução dos Contratos",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(end = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                for (i in 100 downTo 0 step 25) {
                    Text(
                        text = "$i%",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(234.dp)
                        .padding(3.dp)
                ) {
                    val paddingX = 40f
                    val paddingY = 20f
                    val maxY = 100f
                    val minY = 0f
                    val chartHeight = size.height - 2 * paddingY

                    val widthPerYear = (size.width - 2 * paddingX) / (anos.size - 1).coerceAtLeast(1)
                    val heightPerPercent = chartHeight / (maxY - minY)

                    val path = Path()

                    val guideLinePaint = Paint().apply {
                        color = Color.White.copy(alpha = 0.2f)
                    }

                    for (i in 100 downTo 0 step 25) {
                        val y = size.height - paddingY - ((i - minY) / 100f) * chartHeight
                        drawLine(
                            color = guideLinePaint.color,
                            start = Offset(paddingX, y),
                            end = Offset(size.width - paddingX, y),
                            strokeWidth = 2f
                        )
                    }

                    percentagens.forEachIndexed { index, percent ->
                        val x = paddingX + index * widthPerYear
                        val y = size.height - paddingY - ((percent - minY) / 100f) * chartHeight

                        if (index == 0) {
                            path.moveTo(x, y)
                        } else {
                            path.lineTo(x, y)
                        }
                    }

                    drawPath(
                        path = path,
                        color = Color(0xFF3C6E58),
                        style = Stroke(width = 4.dp.toPx())
                    )

                    percentagens.forEachIndexed { index, percent ->
                        val x = paddingX + index * widthPerYear
                        val y = size.height - paddingY - ((percent - minY) / 100f) * chartHeight

                        drawCircle(
                            color = Color.White,
                            radius = 6.dp.toPx(),
                            center = Offset(x, y)
                        )

                        drawIntoCanvas { canvas ->
                            val textPaint = Paint().asFrameworkPaint().apply {
                                textSize = 38f
                                color = android.graphics.Color.WHITE
                                isAntiAlias = true
                                textAlign = android.graphics.Paint.Align.CENTER
                            }
                            canvas.nativeCanvas.drawText("${percent.toInt()}%", x, y - 32, textPaint)
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    anos.forEach { ano ->
                        Text(
                            text = ano,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}