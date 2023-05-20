package com.example.zabalketa

import androidx.room.Embedded
import androidx.room.Relation

data class RegionConNiebla(
    @Embedded val genero: Region,
    @Relation(
        parentColumn = "id",
        entityColumn = "region")
    val regiones: List<Niebla>
)