package com.example.nexusisc.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    // Datos de especialidades centralizados
    private val _especialidades = MutableStateFlow(listOf(
        Especialidad("Ciberseguridad", "Protección de redes y datos contra ataques.", "10 semestres"),
        Especialidad("Inteligencia Artificial", "Modelos de aprendizaje profundo y redes neuronales.", "9 semestres"),
        Especialidad("Desarrollo Fullstack", "Creación de aplicaciones web y móviles modernas.", "9 semestres")
    ))
    val especialidades: StateFlow<List<Especialidad>> = _especialidades
}

data class Especialidad(val nombre: String, val descripcion: String, val duracion: String)