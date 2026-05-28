package com.example.nexusisc.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nexusisc.ui.screens.SplashScreen
import com.example.nexusisc.ui.screens.HomeScreen
import com.example.nexusisc.ui.screens.ContactScreen
import com.example.nexusisc.ui.screens.CampoLaboralScreen
import com.example.nexusisc.ui.screens.QuizScreen
import com.example.nexusisc.ui.screens.CarreraDetailScreen// <-- Agregamos el import de la nueva pantalla

/**
 * NavGraph — Controlador central de navegación de la app ISC Discovery.
 */
@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash_screen"
    ) {
        composable("splash_screen") {
            SplashScreen(navController)
        }
        composable("home_screen") {
            HomeScreen(navController)
        }
        composable("quiz_screen") {
            QuizScreen(navController)
        }
        composable("contact_screen") {
            ContactScreen()
        }
        composable("campo_laboral") {
            CampoLaboralScreen(navController)
        }
        // <-- Agregamos la ruta dinámica para el detalle de la carrera
        composable("carrera_detail/{carreraId}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("carreraId") ?: ""
            CarreraDetailScreen(navController = navController, carreraId = id)
        }
    }
}