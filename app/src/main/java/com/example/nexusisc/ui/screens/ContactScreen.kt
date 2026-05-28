package com.example.nexusisc.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CastForEducation
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nexusisc.R

// ─────────────────────────────────────────────────────────
//  Data model para las especialidades
// ─────────────────────────────────────────────────────────
private data class Especialidad(
    val titulo: String,
    val desc: String,
    val emoji: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen() {
    val context = LocalContext.current

    val especialidades = listOf(
        Especialidad(
            "Ciberseguridad",
            "Protege infraestructuras críticas, detecta vulnerabilidades y defiende sistemas ante amenazas digitales.",
            "🛡️"
        ),
        Especialidad(
            "Inteligencia Artificial",
            "Crea sistemas capaces de aprender, razonar y tomar decisiones. Desde ML hasta redes neuronales.",
            "🤖"
        ),
        Especialidad(
            "Desarrollo Fullstack",
            "Domina el frontend y backend modernos. React, Node.js, bases de datos, APIs y despliegue en la nube.",
            "🌐"
        ),
        Especialidad(
            "Redes y Telecomunicaciones",
            "Diseña e implementa infraestructura de redes empresariales y sistemas de comunicación.",
            "📡"
        )
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Plan de Estudios ISC", fontWeight = FontWeight.Bold)
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ── Sección QR ──
            Text(
                "Escanea para descargar",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold
            )
            Text(
                "el plan de estudios en PDF",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(20.dp))

            ElevatedCard(
                modifier = Modifier.size(260.dp),
                shape = MaterialTheme.shapes.extraLarge,
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.codigo_qr),
                        contentDescription = "QR Plan de Estudios ISC",
                        modifier = Modifier
                            .size(220.dp)
                            .clip(MaterialTheme.shapes.medium)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botones de acción QR
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilledTonalButton(
                    onClick = {
                        // Reemplaza con la URL real de tu PDF
                        val uri = Uri.parse("https://www.itsz.edu.mx/ISC_plan.pdf")
                        context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Descargar PDF")
                }
                FilledTonalButton(
                    onClick = { /* Compartir QR */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.QrCode, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Compartir QR")
                }
            }

            Spacer(modifier = Modifier.height(28.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(28.dp))

            // ── Sección: Cómo llegar ──
            Button(
                onClick = {
                    val gmmIntentUri = Uri.parse("geo:18.8214,-97.1648?q=ITSZ+Campus+Nogales")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    try {
                        context.startActivity(mapIntent)
                    } catch (e: Exception) {
                        // Fallback al navegador si Google Maps no está instalado
                        val webUri = Uri.parse("https://maps.google.com/?q=ITSZ+Nogales+Veracruz")
                        context.startActivity(Intent(Intent.ACTION_VIEW, webUri))
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Icon(Icons.Default.LocationOn, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("📍 Cómo llegar al Campus Nogales")
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ── Sección: Especialidades ──
            Text(
                "Especialidades del Plan ISC",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            )
            Text(
                "Elige el área que más te apasione.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            especialidades.forEachIndexed { index, esp ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Surface(
                            shape = MaterialTheme.shapes.medium,
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            modifier = Modifier.size(48.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = esp.emoji,
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = esp.titulo,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = esp.desc,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}