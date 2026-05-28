package com.example.nexusisc.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.itszapp.model.ITSZData

/**
 * Quiz vocacional del ITSZ.
 *
 * Cada pregunta tiene 5 opciones (A–E), una por carrera.
 * Al responder, se acumula +1 punto en el mapa de puntajes por carrera.
 * El resultado muestra la carrera con mayor puntaje — y las 2 siguientes.
 *
 * Estado con rememberSaveable → sobrevive rotaciones de pantalla.
 */

// Opción dentro de una pregunta
private data class OpcionQuiz(
    val texto: String,
    val carreraId: String   // "isc", "ige", "idc", "if", "iias"
)

// Pregunta del quiz
private data class PreguntaQuiz(
    val pregunta: String,
    val opciones: List<OpcionQuiz>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(navController: NavController) {

    val preguntas = remember {
        listOf(
            PreguntaQuiz(
                "🤔 ¿Qué actividad disfrutarías más en tu día a día de trabajo?",
                listOf(
                    OpcionQuiz("Programar apps y soluciones tecnológicas", "isc"),
                    OpcionQuiz("Dirigir equipos y tomar decisiones de negocio", "ige"),
                    OpcionQuiz("Apoyar a comunidades a mejorar su calidad de vida", "idc"),
                    OpcionQuiz("Trabajar en bosques y conservar el medio ambiente", "if"),
                    OpcionQuiz("Innovar el campo con tecnología agrícola", "iias")
                )
            ),
            PreguntaQuiz(
                "🌍 ¿Qué problema de tu región te gustaría resolver?",
                listOf(
                    OpcionQuiz("La falta de tecnología e infraestructura digital", "isc"),
                    OpcionQuiz("El escaso desarrollo económico y empresarial", "ige"),
                    OpcionQuiz("Las desigualdades sociales en las comunidades nahuas", "idc"),
                    OpcionQuiz("La deforestación y pérdida de ecosistemas serranos", "if"),
                    OpcionQuiz("La baja productividad agrícola y abandono del campo", "iias")
                )
            ),
            PreguntaQuiz(
                "📚 ¿Qué tipo de materias te atraen más?",
                listOf(
                    OpcionQuiz("Matemáticas, lógica y programación", "isc"),
                    OpcionQuiz("Administración, finanzas y mercadotecnia", "ige"),
                    OpcionQuiz("Ciencias sociales, antropología y gestión social", "idc"),
                    OpcionQuiz("Biología, ecología y geografía", "if"),
                    OpcionQuiz("Química, agronomía y biología aplicada", "iias")
                )
            ),
            PreguntaQuiz(
                "🚀 ¿Cómo visualizas tu vida en 10 años?",
                listOf(
                    OpcionQuiz("Trabajando en una empresa tech o con mi propia startup", "isc"),
                    OpcionQuiz("Dirigiendo mi propia empresa o siendo gerente", "ige"),
                    OpcionQuiz("Coordinando proyectos de impacto social en mi región", "idc"),
                    OpcionQuiz("Trabajando en campo, conservando los bosques de Veracruz", "if"),
                    OpcionQuiz("Modernizando el agro y ayudando a productores rurales", "iias")
                )
            ),
            PreguntaQuiz(
                "⚡ ¿Cuál de estas frases te describe mejor?",
                listOf(
                    OpcionQuiz("Pienso en código — si existe un problema, existe una solución digital", "isc"),
                    OpcionQuiz("Los negocios son mi pasión — me gusta liderar y emprender", "ige"),
                    OpcionQuiz("Mi fuerza es escuchar — entiendo las necesidades de mi comunidad", "idc"),
                    OpcionQuiz("Amo la naturaleza — soy feliz caminando entre los bosques serranos", "if"),
                    OpcionQuiz("El campo me llama — quiero modernizar la agricultura de mi región", "iias")
                )
            ),
            PreguntaQuiz(
                "🏔️ El ITSZ está en la zona de Altas Montañas de Veracruz. ¿Qué te emociona más de estudiar aquí?",
                listOf(
                    OpcionQuiz("Crear proyectos tech que impacten la región serrana", "isc"),
                    OpcionQuiz("Aplicar negocios e innovación en las empresas locales", "ige"),
                    OpcionQuiz("Trabajar directo con las comunidades indígenas nahuas", "idc"),
                    OpcionQuiz("Estudiar y proteger los bosques de niebla de Veracruz", "if"),
                    OpcionQuiz("Impulsar el campo y la agroindustria de las Altas Montañas", "iias")
                )
            )
        )
    }

    // ── Estado con rememberSaveable ──
    var currentIdx  by rememberSaveable { mutableStateOf(0) }
    var selectedOpt by rememberSaveable { mutableStateOf<Int?>(null) }
    var finished    by rememberSaveable { mutableStateOf(false) }

    // Puntaje por carrera (serializado como String CSV para rememberSaveable)
    var puntajesStr by rememberSaveable {
        mutableStateOf("isc:0,ige:0,idc:0,if:0,iias:0")
    }

    // Parse/encode helpers
    fun parsePuntajes(): MutableMap<String, Int> {
        val map = mutableMapOf<String, Int>()
        puntajesStr.split(",").forEach { entry ->
            val (k, v) = entry.split(":")
            map[k] = v.toInt()
        }
        return map
    }
    fun encodePuntajes(map: Map<String, Int>) =
        map.entries.joinToString(",") { "${it.key}:${it.value}" }

    val progress by animateFloatAsState(
        targetValue = if (finished) 1f else currentIdx.toFloat() / preguntas.size,
        animationSpec = tween(600, easing = FastOutSlowInEasing),
        label = "progress"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (finished) "Tu Resultado" else "¿Qué carrera es para ti?",
                        fontWeight = FontWeight.Bold
                    )
                },
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {

            if (!finished) {
                // Barra de progreso
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Pregunta ${currentIdx + 1} de ${preguntas.size}",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        "ITSZ • Test Vocacional",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth().height(10.dp),
                    strokeCap = StrokeCap.Round,
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
                Spacer(Modifier.height(28.dp))
            }

            AnimatedContent(
                targetState = finished,
                transitionSpec = {
                    fadeIn(tween(400)) + slideInVertically(tween(400)) { it / 3 } togetherWith
                            fadeOut(tween(200))
                },
                label = "quiz_content"
            ) { isFinished ->

                if (!isFinished) {
                    // ── Pantalla de pregunta ──
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            preguntas[currentIdx].pregunta,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 24.dp)
                        )

                        preguntas[currentIdx].opciones.forEachIndexed { index, opcion ->
                            val isSelected = selectedOpt == index
                            val optScale by animateFloatAsState(
                                targetValue = if (isSelected) 1.02f else 1f,
                                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                                label = "opt_scale_$index"
                            )
                            // Color de la carrera de esta opción
                            val carreraColor = ITSZData.carreras
                                .find { it.id == opcion.carreraId }
                                ?.let { parseColor(it.colorHex) }
                                ?: MaterialTheme.colorScheme.primary

                            OutlinedCard(
                                onClick = { selectedOpt = index },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp)
                                    .scale(optScale),
                                colors = CardDefaults.outlinedCardColors(
                                    containerColor = if (isSelected)
                                        carreraColor.copy(alpha = 0.10f)
                                    else
                                        MaterialTheme.colorScheme.surface
                                ),
                                border = if (isSelected)
                                    CardDefaults.outlinedCardBorder(enabled = true)
                                else
                                    CardDefaults.outlinedCardBorder(enabled = false)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 14.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    if (isSelected) {
                                        Icon(
                                            Icons.Default.CheckCircle,
                                            contentDescription = null,
                                            tint = carreraColor,
                                            modifier = Modifier.size(22.dp)
                                        )
                                    } else {
                                        Surface(
                                            shape = RoundedCornerShape(50),
                                            color = MaterialTheme.colorScheme.secondaryContainer,
                                            modifier = Modifier.size(22.dp)
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Text(
                                                    ('A' + index).toString(),
                                                    style = MaterialTheme.typography.labelSmall,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                    }
                                    Text(
                                        opcion.texto,
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }

                        Spacer(Modifier.height(24.dp))

                        Button(
                            onClick = {
                                selectedOpt?.let { chosen ->
                                    val carreraId = preguntas[currentIdx].opciones[chosen].carreraId
                                    val puntajes = parsePuntajes()
                                    puntajes[carreraId] = (puntajes[carreraId] ?: 0) + 1
                                    puntajesStr = encodePuntajes(puntajes)
                                    selectedOpt = null
                                    if (currentIdx < preguntas.size - 1) {
                                        currentIdx++
                                    } else {
                                        finished = true
                                    }
                                }
                            },
                            enabled = selectedOpt != null,
                            modifier = Modifier.fillMaxWidth().height(52.dp),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Text(
                                if (currentIdx < preguntas.size - 1) "Siguiente →"
                                else "Ver mi resultado 🎯",
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(Modifier.height(40.dp))
                    }

                } else {
                    // ── Pantalla de resultado ──
                    val puntajes = parsePuntajes()
                    val rankingCarreras = ITSZData.carreras
                        .sortedByDescending { puntajes[it.id] ?: 0 }
                    val carreraTop = rankingCarreras.first()
                    val carreraColor = parseColor(carreraTop.colorHex)

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(Modifier.height(8.dp))
                        Text(carreraTop.emoji, style = MaterialTheme.typography.displayLarge)
                        Spacer(Modifier.height(10.dp))
                        Text(
                            "¡Tu carrera ideal en el ITSZ!",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            carreraTop.nombre,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = carreraColor,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Text(
                            carreraTop.abreviacion,
                            style = MaterialTheme.typography.titleMedium,
                            color = carreraColor.copy(alpha = 0.7f)
                        )

                        Spacer(Modifier.height(20.dp))

                        // Resultado detallado
                        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Text(
                                    carreraTop.descripcionLarga,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(Modifier.height(14.dp))
                                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                                Spacer(Modifier.height(10.dp))
                                Text(
                                    "💼 Salario inicial: ${carreraTop.salarioInicial}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = carreraColor
                                )
                            }
                        }

                        // Otras opciones compatibles
                        if (rankingCarreras.size > 1) {
                            Spacer(Modifier.height(20.dp))
                            Text(
                                "También podrías considerar:",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(Modifier.height(8.dp))
                            rankingCarreras.drop(1).take(2).forEach { c ->
                                val pts = puntajes[c.id] ?: 0
                                if (pts > 0) {
                                    OutlinedCard(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(14.dp),
                                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(c.emoji, style = MaterialTheme.typography.titleMedium)
                                            Column(Modifier.weight(1f)) {
                                                Text(c.abreviacion, fontWeight = FontWeight.Bold,
                                                    style = MaterialTheme.typography.labelMedium,
                                                    color = parseColor(c.colorHex))
                                                Text(c.nombre, style = MaterialTheme.typography.bodySmall)
                                            }
                                            Text(
                                                "$pts pts",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(24.dp))

                        // Acciones
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OutlinedButton(
                                onClick = {
                                    currentIdx = 0
                                    selectedOpt = null
                                    finished = false
                                    puntajesStr = "isc:0,ige:0,idc:0,if:0,iias:0"
                                },
                                modifier = Modifier.weight(1f).height(50.dp)
                            ) {
                                Icon(Icons.Default.Refresh, null, Modifier.size(16.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("Repetir")
                            }
                            Button(
                                onClick = {
                                    navController.navigate("carrera_detail/${carreraTop.id}")
                                },
                                modifier = Modifier.weight(1f).height(50.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = carreraColor)
                            ) {
                                Text("Ver ${carreraTop.abreviacion} →", fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(Modifier.height(40.dp))
                    }
                }
            }
        }
    }
}


