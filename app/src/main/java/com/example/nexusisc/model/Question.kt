package com.example.nexusisc.model

data class Question(
    val text: String,
    val options: List<String>,
    val correctOptionIndex: Int
)