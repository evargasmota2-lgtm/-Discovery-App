package com.example.nexusisc.ui.components

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhatsAppButton() {
    val context = LocalContext.current
    // ⚠️ Reemplaza con el número real de Admisiones del ITSZ
    val phoneNumber = "522721578910"
    val message = "Hola, vengo de la app Nexus ISC y me interesa conocer más sobre la carrera de ISC."

    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            PlainTooltip {
                Text("Contactar a Admisiones")
            }
        },
        state = rememberTooltipState()
    ) {
        FloatingActionButton(
            onClick = {
                try {
                    val uri = Uri.parse("whatsapp://send?phone=$phoneNumber&text=${Uri.encode(message)}")
                    context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                } catch (e: Exception) {
                    try {
                        val webUri = Uri.parse("https://wa.me/$phoneNumber?text=${Uri.encode(message)}")
                        context.startActivity(Intent(Intent.ACTION_VIEW, webUri))
                    } catch (ex: Exception) {
                        Toast.makeText(context, "No se pudo abrir WhatsApp", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            containerColor = Color(0xFF25D366),
            contentColor = Color.White
        ) {
            Icon(Icons.Default.Chat, contentDescription = "Abrir WhatsApp con Admisiones")
        }
    }
}