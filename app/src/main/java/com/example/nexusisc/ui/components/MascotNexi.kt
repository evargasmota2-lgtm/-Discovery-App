package com.example.nexusisc.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp

@Composable
fun MascotNexi() {
    var isHappy by remember { mutableStateOf(false) }
    var tapCount by remember { mutableStateOf(0) }

    val dialogs = listOf(
        "¡Hola! Soy Nexi 🤖",
        "¿Listo para programar el futuro?",
        "Cero lag en tu cerebro ⚡",
        "¡Hackea tu potencial en el ITSZ!",
        "¿Te gustan los videojuegos? ¡Créalos!",
        "Los ingenieros construyen el mañana 🚀",
        "El código es el lenguaje del siglo XXI 💻",
        "if (estudias_ISC) { futuro = garantizado; } 😄",
        "¡Tú también puedes crear la próxima app viral!",
        "La IA no reemplaza ingenieros — ¡los necesita!"
    )

    var currentDialog by remember { mutableStateOf(dialogs[0]) }

    // Animación de flotación continua
    val infiniteTransition = rememberInfiniteTransition(label = "floating")
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -12f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offsetY"
    )

    // Rotación sutil al flotar
    val rotation by infiniteTransition.animateFloat(
        initialValue = -2f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotation"
    )

    // Animación de escala al tocar
    val scale by animateFloatAsState(
        targetValue = if (isHappy) 1.25f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "scale"
    )

    // Regresa al tamaño normal tras 400ms
    LaunchedEffect(tapCount) {
        if (isHappy) {
            kotlinx.coroutines.delay(400)
            isHappy = false
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        // Burbuja de diálogo
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.tertiaryContainer,
            tonalElevation = 2.dp,
            modifier = Modifier.padding(bottom = 10.dp)
        ) {
            Text(
                text = currentDialog,
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }

        // Icono mascota con animaciones
        Icon(
            imageVector = if (isHappy) Icons.Default.SmartToy else Icons.Default.Face,
            contentDescription = "Toca a Nexi para que hable",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(80.dp)
                .offset(y = offsetY.dp)
                .rotate(rotation)
                .scale(scale)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    isHappy = true
                    tapCount++
                    currentDialog = dialogs.random()
                }
        )

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "¡Tócame!",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}