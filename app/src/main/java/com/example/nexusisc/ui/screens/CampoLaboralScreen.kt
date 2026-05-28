package com.example.nexusisc.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// ─────────────────────────────────────────────────────────
//  Modelos de datos locales
// ─────────────────────────────────────────────────────────
private data class JobRole(
    val emoji: String,
    val titulo: String,
    val salarioMXN: String,
    val salarioUSD: String,
    val nivel: String,           // "Junior" / "Mid" / "Senior"
    val skills: List<String>
)

private data class SectorItem(
    val nombre: String,
    val descripcion: String,
    val demanda: Int             // porcentaje 0-100 para la barra visual
)

// ─────────────────────────────────────────────────────────
//  CampoLaboralScreen
// ─────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoLaboralScreen(navController: NavController) {

    // ── Datos ──
    val roles = remember {
        listOf(
            JobRole(
                "🛡️", "Especialista en Ciberseguridad",
                "$25,000 – $55,000", "$1,200 – $4,500",
                "Mid – Senior",
                listOf("Pentesting", "SIEM", "Firewalls", "ISO 27001")
            ),
            JobRole(
                "🤖", "Ingeniero de ML / IA",
                "$30,000 – $70,000", "$1,500 – $6,000",
                "Mid – Senior",
                listOf("Python", "TensorFlow", "PyTorch", "MLOps")
            ),
            JobRole(
                "🌐", "Desarrollador Fullstack",
                "$18,000 – $45,000", "$900 – $3,800",
                "Junior – Senior",
                listOf("React", "Node.js", "AWS", "PostgreSQL")
            ),
            JobRole(
                "📱", "Desarrollador Mobile",
                "$20,000 – $50,000", "$1,000 – $4,200",
                "Junior – Senior",
                listOf("Android", "iOS", "Flutter", "Kotlin")
            ),
            JobRole(
                "☁️", "Arquitecto Cloud / DevOps",
                "$35,000 – $80,000", "$1,800 – $7,000",
                "Senior",
                listOf("AWS", "Docker", "Kubernetes", "Terraform")
            ),
            JobRole(
                "🗄️", "Administrador de Redes",
                "$15,000 – $35,000", "$700 – $2,800",
                "Junior – Mid",
                listOf("Cisco", "CCNA", "VPN", "SDN")
            )
        )
    }

    val sectores = remember {
        listOf(
            SectorItem("Fintech & Banca Digital", "Bancos, fintechs y empresas de pagos electrónicos.", 92),
            SectorItem("Manufactura 4.0 (nearshoring)", "Industria automotriz y electrónica en México.", 88),
            SectorItem("Salud Digital & Telemedicina", "Hospitales, laboratorios y plataformas de salud.", 75),
            SectorItem("Gobierno y Sector Público", "Secretarías, IMSS, SAT y sistemas municipales.", 68),
            SectorItem("E-commerce & Retail Tech", "Plataformas de venta en línea y logística.", 85)
        )
    }

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Campo Laboral ISC", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.primary
                )
            )
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
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {

                // ── Header banner ──
                item {
                    Spacer(Modifier.height(20.dp))
                    BannerDestacado()
                    Spacer(Modifier.height(28.dp))
                }

                // ── Sección: Roles y salarios ──
                item {
                    SectionHeader(
                        emoji = "💼",
                        titulo = "Roles y Rangos Salariales",
                        subtitulo = "Salarios mensuales en México (MXN) y remoto (USD)"
                    )
                    Spacer(Modifier.height(12.dp))
                }

                itemsIndexed(roles) { index, role ->
                    // Stagger animation por índice
                    var itemVisible by remember { mutableStateOf(false) }
                    LaunchedEffect(Unit) {
                        kotlinx.coroutines.delay(index * 80L)
                        itemVisible = true
                    }
                    AnimatedVisibility(
                        visible = itemVisible,
                        enter = fadeIn(tween(300)) + slideInHorizontally(tween(350)) { -40 }
                    ) {
                        JobRoleCard(role)
                    }
                }

                // ── Sección: Sectores con mayor demanda ──
                item {
                    Spacer(Modifier.height(28.dp))
                    SectionHeader(
                        emoji = "📊",
                        titulo = "Sectores con Mayor Demanda",
                        subtitulo = "Dónde más contratan ingenieros en Sistemas"
                    )
                    Spacer(Modifier.height(12.dp))
                }

                itemsIndexed(sectores) { _, sector ->
                    SectorDemandaCard(sector)
                }

                // ── Datos motivadores finales ──
                item {
                    Spacer(Modifier.height(28.dp))
                    DatosRapidosCard()
                    Spacer(Modifier.height(60.dp))
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────
//  Componentes internos de esta pantalla
// ─────────────────────────────────────────────────────────

@Composable
private fun BannerDestacado() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.horizontalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.tertiary
                    )
                )
            )
            .padding(20.dp)
    ) {
        Column {
            Text(
                "🌎 México necesita +87,000",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
            Text(
                "ingenieros en Sistemas por año",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Fuente: CANIETI 2024 — Solo el 43 % de las vacantes tech se cubren.",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
private fun SectionHeader(emoji: String, titulo: String, subtitulo: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "$emoji $titulo",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = subtitulo,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@OptIn(ExperimentalLayoutApi::class) // <-- Aquí está la corrección principal
@Composable
private fun JobRoleCard(role: JobRole) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Título del rol
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = role.emoji,
                    style = MaterialTheme.typography.titleLarge
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = role.titulo,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = role.nivel,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }

            Spacer(Modifier.height(10.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            Spacer(Modifier.height(10.dp))

            // Salarios
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SalarioChip(label = "🇲🇽 MXN", valor = role.salarioMXN)
                SalarioChip(label = "🌐 Remoto USD", valor = role.salarioUSD)
            }

            Spacer(Modifier.height(12.dp))

            // Skills chips
            androidx.compose.foundation.layout.FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                role.skills.forEach { skill ->
                    SuggestionChip(
                        onClick = {},
                        label = { Text(skill, style = MaterialTheme.typography.labelSmall) }
                    )
                }
            }
        }
    }
}

@Composable
private fun SalarioChip(label: String, valor: String) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.secondaryContainer,
        modifier = Modifier.wrapContentSize()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelXSmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
            )
            Text(
                text = valor,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

// Extension para labelXSmall si no existe en el tema — usa labelSmall como fallback
private val Typography.labelXSmall get() = this.labelSmall

@Composable
private fun SectorDemandaCard(sector: SectorItem) {
    val progress by animateFloatAsState(
        targetValue = sector.demanda / 100f,
        animationSpec = tween(900, easing = FastOutSlowInEasing),
        label = "demanda_progress"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = sector.nombre,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "${sector.demanda}%",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                text = sector.descripcion,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(vertical = 6.dp)
            )
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
            )
        }
    }
}

@Composable
private fun DatosRapidosCard() {
    val datos = listOf(
        Triple("🎓", "Tasa de empleo", "94 % al año de egresar"),
        Triple("📈", "Crecimiento del sector", "+22 % anual en México"),
        Triple("🏠", "Trabajo remoto", "70 % de las vacantes tech"),
        Triple("💡", "Startups fundadas", "ISC lidera en emprendimiento tech")
    )

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                "⚡ Datos Rápidos",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(14.dp))
            datos.forEach { (emoji, titulo, valor) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(emoji, style = MaterialTheme.typography.titleMedium)
                    Column {
                        Text(
                            titulo,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            valor,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}