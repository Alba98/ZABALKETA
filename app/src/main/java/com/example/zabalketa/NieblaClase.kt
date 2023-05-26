package com.example.zabalketa

data class NieblaClase(
    val id: Int,
    val fecha: String,
    val username: String,
    val region: String,
    val hayNiebla: Boolean,
    val densidad: String,
    val hayLluvia: Boolean,
    val duracionLluvia: Int,
    val hayCorteAgua: Boolean,
    val duracionCorteAgua: Int,
    val incidencia: String
)

/*
data class NieblaClase(
    val id: Int,
    val fecha: String,
    val username: String,
    val region: String,
    val hayNiebla: Boolean,
    val densidad: String,
    val franja: Int,
    val hayLluvia: Boolean,
    val duracionLluvia: Int,
    val hayCorteAgua: Boolean,
    val duracionCorteAgua: Int,
    val incidencia: String
)
*/