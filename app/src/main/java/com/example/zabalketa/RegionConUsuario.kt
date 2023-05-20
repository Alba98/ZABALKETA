package com.example.zabalketa

import androidx.room.Embedded
import androidx.room.Relation

data class RegionConUsuario(
    @Embedded val genero: Region,
    @Relation(
        parentColumn = "id",
        entityColumn = "region")
    val peliculas: List<Usuario>
)