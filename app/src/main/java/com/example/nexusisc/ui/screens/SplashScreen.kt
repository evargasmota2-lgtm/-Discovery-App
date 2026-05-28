package com.example.nexusisc.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nexusisc.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navController: NavController) {

    val logoAlpha = remember { Animatable(0f) }
    val logoScale = remember { Animatable(0.75f) }
    val textAlpha = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        // Lanza las dos animaciones del logo en paralelo y espera a que ambas terminen
        val j1 = launch { logoAlpha.animateTo(1f, tween(600, easing = FastOutSlowInEasing)) }
        val j2 = launch { logoScale.animateTo(1f, tween(700, easing = FastOutSlowInEasing)) }
        j1.join()
        j2.join()

        // Texto aparece después
        delay(300)
        textAlpha.animateTo(1f, tween(500, easing = LinearEasing))

        // Navega a Home
        delay(1200)
        navController.navigate("home_screen") {
            popUpTo("splash_screen") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Logo con animación de scale + fade-in
            Image(
                painter = painterResource(id = R.drawable.logo_isc),
                contentDescription = "Logo ISC",
                modifier = Modifier
                    .size(160.dp)
                    .scale(logoScale.value)
                    .alpha(logoAlpha.value)
            )

            // Nombre de la app
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.alpha(textAlpha.value)
            ) {
                Text(
                    text = "NEXUS ISC",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 3.sp
                )
                Text(
                    text = "ITSZ Nogales • Ingeniería en Sistemas",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}