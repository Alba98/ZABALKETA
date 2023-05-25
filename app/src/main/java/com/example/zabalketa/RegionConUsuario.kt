package com.example.zabalketa

import androidx.room.Embedded
import androidx.room.Relation

data class RegionConUsuario(
    @Embedded val region: Region,
    @Relation(
        parentColumn = "id",
        entityColumn = "region")
    val usuarios: List<Usuario>
)