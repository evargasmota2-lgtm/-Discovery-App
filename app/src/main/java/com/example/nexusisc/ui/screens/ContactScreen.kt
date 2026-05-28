package com.example.nexusisc.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nexusisc.R

// Importamos tu componente de botón flotante de respaldo
import com.example.nexusisc.ui.components.WhatsAppButton

private data class Especialidad(
    val titulo: String,
    val desc: String,
    val emoji: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen() {
    val context = LocalContext.current

    // Estado para controlar la visibilidad del QR
    var mostrarQR by remember { mutableStateOf(false) }

    val ForumEspecialidades = listOf(
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
        },
        floatingActionButton = {
            WhatsAppButton()
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
            Text(
                "Plan de Estudios ISC",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))

            // BOTÓN PRINCIPAL: Solo se muestra si el QR está oculto
            if (!mostrarQR) {
                Button(
                    onClick = { mostrarQR = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Ver Código QR del Plan", fontSize = MaterialTheme.typography.titleMedium.fontSize)
                }
            }

            // SECCIÓN DESPLEGABLE: Contiene el QR y las acciones posteriores
            AnimatedVisibility(
                visible = mostrarQR,
                enter = fadeIn() + expandVertically()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Escanea el código para descargar el PDF",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

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
                            // Renderizado de la imagen qr_final
                            Image(
                                painter = painterResource(id = R.mipmap.ic_launcher), // <- El logo de tu app
                                contentDescription = "QR Plan de Estudios",
                                modifier = Modifier.size(220.dp).clip(MaterialTheme.shapes.medium)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botones secundarios que aparecen junto al QR
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilledTonalButton(
                            onClick = {
                                val uri = Uri.parse("https://www.itsz.edu.mx/ISC_plan.pdf")
                                context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Descargar")
                        }
                        FilledTonalButton(
                            onClick = { /* Compartir */ },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Compartir")
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // BOTÓN DE WHATSAPP EN EL CUERPO: Se activa como segunda opción tras desplegar el QR
                    Button(
                        onClick = {
                            val encodedMessage = Uri.encode("Hola, vengo de la app Nexus ISC y deseo más información.")
                            val url = "https://api.whatsapp.com/send?phone=522721578910&text=$encodedMessage"
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            intent.setPackage("com.whatsapp")
                            try {
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                intent.setPackage(null)
                                context.startActivity(intent)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366))
                    ) {
                        Text("💬 Enviar un WhatsApp a Admisiones", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(24.dp))

            // ── Sección de ubicación ──
            Button(
                onClick = {
                    val gmmIntentUri = Uri.parse("geo:18.8214,-97.1648?q=ITSZ+Campus+Nogales")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    try {
                        context.startActivity(mapIntent)
                    } catch (e: Exception) {
                        val webUri = Uri.parse("https://maps.google.com/?q=ITSZ+Nogales+Veracruz")
                        context.startActivity(Intent(Intent.ACTION_VIEW, webUri))
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Icon(Icons.Default.LocationOn, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("📍 Cómo llegar al Campus Nogales")
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ── Lista de especialidades académicas ──
            Text(
                "Especialidades del Plan ISC",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            ForumEspecialidades.forEach { esp ->
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Surface(
                            shape = MaterialTheme.shapes.medium,
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            modifier = Modifier.size(48.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(text = esp.emoji, style = MaterialTheme.typography.titleLarge)
                            }
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = esp.titulo, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = esp.desc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}