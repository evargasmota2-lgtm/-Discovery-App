package com.example.nexusisc.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.itszapp.model.Carrera
import com.example.itszapp.model.ITSZData

/**
 * CarreraDetailScreen — Pantalla de detalle para cada carrera del ITSZ.
 * Recibe el [carreraId] desde el NavGraph y busca los datos en ITSZData.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarreraDetailScreen(navController: NavController, carreraId: String) {
    val context = LocalContext.current

    // Busca la carrera por ID; si no existe regresa a Home
    val carrera = ITSZData.carreras.find { it.id == carreraId }
    if (carrera == null) {
        LaunchedEffect(Unit) { navController.popBackStack() }
        return
    }

    val carreraColor = parseColor(carrera.colorHex)
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(carrera.abreviacion, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = carreraColor.copy(alpha = 0.15f),
                    titleContentColor = carreraColor,
                    navigationIconContentColor = carreraColor
                )
            )
        }
    ) { padding ->

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(300)) + slideInVertically(tween(380)) { 40 }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // ── Hero banner ──
                item {
                    HeroBanner(carrera, carreraColor)
                }

                // ── Datos rápidos ──
                item {
                    Spacer(Modifier.height(20.dp))
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        DatosRapidosRow(carrera, carreraColor)
                    }
                }

                // ── Objetivo ──
                item {
                    Spacer(Modifier.height(20.dp))
                    SeccionTexto(
                        titulo = "🎯 Objetivo de la Carrera",
                        contenido = carrera.objetivo,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                // ── Descripción ──
                item {
                    Spacer(Modifier.height(16.dp))
                    SeccionTexto(
                        titulo = "📖 ¿Por qué elegir ${carrera.abreviacion}?",
                        contenido = carrera.descripcionLarga,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                // ── Materias clave ──
                item {
                    Spacer(Modifier.height(20.dp))
                    ListaSeccion(
                        titulo = "📚 Materias Clave",
                        items = carrera.materiasClave,
                        color = carreraColor,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                // ── Habilidades ──
                item {
                    Spacer(Modifier.height(16.dp))
                    ChipsSeccion(
                        titulo = "⚡ Habilidades que Desarrollarás",
                        items = carrera.habilidades,
                        color = carreraColor,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                // ── Campo laboral ──
                item {
                    Spacer(Modifier.height(20.dp))
                    ListaSeccion(
                        titulo = "💼 Campo Laboral",
                        items = carrera.campoLaboral,
                        color = carreraColor,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                // ── Salario ──
                item {
                    Spacer(Modifier.height(20.dp))
                    SalarioCard(carrera, carreraColor, modifier = Modifier.padding(horizontal = 16.dp))
                }

                // ── Acciones ──
                item {
                    Spacer(Modifier.height(24.dp))
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Ir al sitio oficial del ITSZ
                        Button(
                            onClick = {
                                val uri = Uri.parse(ITSZData.SITIO_WEB)
                                context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = carreraColor
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.OpenInNew, null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("Ver en el sitio oficial del ITSZ", fontWeight = FontWeight.Bold)
                        }

                        // Cómo llegar
                        OutlinedButton(
                            onClick = {
                                val uri = Uri.parse(
                                    "geo:${ITSZData.LAT},${ITSZData.LNG}?q=Instituto+Tecnológico+Superior+de+Zongolica"
                                )
                                try {
                                    val intent = Intent(Intent.ACTION_VIEW, uri)
                                    intent.setPackage("com.google.android.apps.maps")
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    val webUri = Uri.parse("https://maps.google.com/?q=ITSZ+Zongolica+Veracruz")
                                    context.startActivity(Intent(Intent.ACTION_VIEW, webUri))
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.LocationOn, null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("Cómo llegar al Campus Zongolica")
                        }
                    }
                }

                item { Spacer(Modifier.height(60.dp)) }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────
//  Componentes internos
// ─────────────────────────────────────────────────────────

@Composable
private fun HeroBanner(carrera: Carrera, color: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(
                Brush.verticalGradient(
                    listOf(
                        color,
                        color.copy(alpha = 0.6f)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(carrera.emoji, style = MaterialTheme.typography.displayMedium)
            Spacer(Modifier.height(8.dp))
            Text(
                carrera.nombre,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 24.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "ITSZ • Desde ${carrera.anioApertura}",
                style = MaterialTheme.typography.labelMedium,
                color = Color.White.copy(alpha = 0.85f)
            )
        }
    }
}

@Composable
private fun DatosRapidosRow(carrera: Carrera, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        listOf(
            "⏱️" to carrera.duracion,
            "🎓" to carrera.modalidad.substringBefore("•").trim()
        ).forEach { (emoji, texto) ->
            ElevatedCard(
                modifier = Modifier.weight(1f),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(emoji, style = MaterialTheme.typography.titleMedium)
                    Text(
                        texto,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
        ElevatedCard(
            modifier = Modifier.weight(1f),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("📈", style = MaterialTheme.typography.titleMedium)
                Text(
                    "Demanda ${carrera.demandaLaboral}%",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    color = color
                )
            }
        }
    }
}

@Composable
private fun SeccionTexto(titulo: String, contenido: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            titulo,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(8.dp))
        Text(
            contenido,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
        )
    }
}

@Composable
private fun ListaSeccion(
    titulo: String,
    items: List<String>,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            titulo,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(10.dp))
        items.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(18.dp)
                )
                Text(item, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ChipsSeccion(
    titulo: String,
    items: List<String>,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            titulo,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(10.dp))
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items.forEach { skill ->
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = color.copy(alpha = 0.12f),
                    modifier = Modifier.padding(vertical = 3.dp)
                ) {
                    Text(
                        skill,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = color,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun SalarioCard(carrera: Carrera, color: Color, modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = color.copy(alpha = 0.12f),
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Default.Work,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Column {
                Text(
                    "Salario inicial estimado",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    carrera.salarioInicial,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = color
                )
                Text(
                    "mensual • México",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}