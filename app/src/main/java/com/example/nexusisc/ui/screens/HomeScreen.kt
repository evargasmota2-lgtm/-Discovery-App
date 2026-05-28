package com.example.nexusisc.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.itszapp.model.Carrera
import com.example.itszapp.model.ITSZData
import com.example.nexusisc.ui.components.MascotNexi
import com.example.nexusisc.ui.components.WhatsAppButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = ITSZData.ABREVIACION,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = "Zongolica • TecNM",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                WhatsAppButton()
                ExtendedFloatingActionButton(
                    onClick = { navController.navigate("quiz_screen") },
                    icon = { Icon(Icons.Default.Assignment, contentDescription = null) },
                    text = { Text("¿Qué carrera es para mí?") },
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }
    ) { padding ->

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(350)) + slideInVertically(tween(400)) { 50 }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    Spacer(Modifier.height(12.dp))
                    MascotNexi()
                }

                item {
                    Spacer(Modifier.height(8.dp))
                    BannerITSZ(navController)
                    Spacer(Modifier.height(24.dp))
                }

                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            Icons.Default.School,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(22.dp)
                        )
                        Text(
                            text = "Oferta Educativa",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Text(
                        text = "5 carreras • Campus Zongolica, Veracruz",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(start = 30.dp, bottom = 12.dp)
                    )
                }

                // Card por carrera con animación escalonada
                itemsIndexed(ITSZData.carreras) { index, carrera ->
                    var itemVisible by remember { mutableStateOf(false) }
                    LaunchedEffect(Unit) {
                        kotlinx.coroutines.delay(index * 90L)
                        itemVisible = true
                    }
                    AnimatedVisibility(
                        visible = itemVisible,
                        enter = fadeIn(tween(300)) + slideInHorizontally(tween(350)) { -40 }
                    ) {
                        CarreraCard(
                            carrera = carrera,
                            onClick = { navController.navigate("carrera_detail/${carrera.id}") }
                        )
                    }
                }

                item {
                    Spacer(Modifier.height(28.dp))
                    DatosInstitucionales()
                }

                item { Spacer(Modifier.height(150.dp)) }
            }
        }
    }
}

@Composable
private fun BannerITSZ(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.horizontalGradient(
                    listOf(Color(0xFF0D47A1), Color(0xFF1B5E20))
                )
            )
            .clickable { navController.navigate("quiz_screen") }
            .padding(20.dp)
    ) {
        Column {
            Text(
                "🏔️ Altas Montañas de Veracruz",
                style = MaterialTheme.typography.labelMedium,
                color = Color.White.copy(alpha = 0.8f)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "Tu futuro comienza\nen Zongolica",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Fundado 2002 • TecNM • Modelo Dual desde 2014",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.75f)
            )
            Spacer(Modifier.height(12.dp))
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color.White.copy(alpha = 0.2f)
            ) {
                Text(
                    "Toca para descubrir qué carrera es para ti →",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
fun CarreraCard(carrera: Carrera, onClick: () -> Unit) {
    val carreraColor = parseColor(carrera.colorHex)

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = carreraColor.copy(alpha = 0.12f),
                modifier = Modifier.size(56.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(carrera.emoji, style = MaterialTheme.typography.headlineSmall)
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = carreraColor.copy(alpha = 0.15f)
                ) {
                    Text(
                        carrera.abreviacion,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = carreraColor,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    carrera.nombre,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    lineHeight = MaterialTheme.typography.titleSmall.lineHeight
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    carrera.descripcionCorta,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
                Spacer(Modifier.height(8.dp))

                // Barra de demanda laboral
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "Demanda",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    val progress by animateFloatAsState(
                        targetValue = carrera.demandaLaboral / 100f,
                        animationSpec = tween(800, easing = FastOutSlowInEasing),
                        label = "demanda_${carrera.id}"
                    )
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .weight(1f)
                            .height(5.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = carreraColor,
                        trackColor = carreraColor.copy(alpha = 0.15f)
                    )
                    Text(
                        "${carrera.demandaLaboral}%",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = carreraColor
                    )
                }
            }
        }
    }
}

@Composable
private fun DatosInstitucionales() {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                "🏛️ Instituto Tecnológico Superior de Zongolica",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(10.dp))
            listOf(
                "📅 Fundado" to "2002 — pionero en la región",
                "🎓 Sistema" to ITSZData.PERTENECE_A,
                "👥 Matrícula" to ITSZData.MATRICULA_TOTAL,
                "📍 Dirección" to "Km 4 Carr. a La Compañía, Tepetitlanapa",
                "📚 Modelo" to "Educación Dual — práctica desde sem. 1",
                "📞 Teléfono" to ITSZData.TELEFONO
            ).forEach { (etiqueta, valor) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        etiqueta,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.width(100.dp)
                    )
                    Text(
                        valor,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

fun parseColor(hex: String): Color = try {
    Color(android.graphics.Color.parseColor(hex))
} catch (e: Exception) { Color(0xFF1565C0) }

