package com.example.nexusisc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
// IMPORTANTE: Cambia 'ui.theme' por el nombre real de tu carpeta de tema
import com.example.nexusisc.ui.theme.NexusISCTheme
import com.example.nexusisc.navigation.NavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // REVISA AQUÍ: Si tu proyecto se llama "NexusISC",
            // el tema se llama "NexusISCTheme".
            // Si el error sigue en rojo, borra "NexusISCTheme"
            // y deja que el autocompletar te sugiera el nombre correcto.
            NexusISCTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    NavGraph()
                }
            }
        }
    }
}