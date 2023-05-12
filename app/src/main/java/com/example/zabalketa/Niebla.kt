package com.example.zabalketa

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "tabla_nieblas")
data class Niebla(
    @PrimaryKey (autoGenerate = true)
    var id: Int = 0,
    @NotNull @ColumnInfo (name = "fecha")
    var fecha: String = "",
    @NotNull @ColumnInfo (name = "region")
    var region: String = "",
    @NotNull @ColumnInfo (name = "descripcion")
    var descripcion: String = "",
) {}