package com.example.zabalketa

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.sql.Date

@Entity(tableName = "tabla_nieblas",
    indices = [Index(value = ["fecha", "idUsuario"], unique = true)])
data class Niebla(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @NotNull @ColumnInfo(name = "fecha")
    var fecha: String = "",
    // del usuario puedo sacar su region
    @NotNull @ColumnInfo(name = "idUsuario")
    var idUsuario: Int = 0,
    @NotNull @ColumnInfo(name = "hayNiebla")
    var hayNiebla: Boolean = false,
    @NotNull @ColumnInfo(name = "idDensidad")
    var idDensidad: Int = 0,
    @NotNull @ColumnInfo(name = "idFranja")
    var idFranja: Int = 0,
    @NotNull @ColumnInfo(name = "hayLluvia")
    var hayLluvia: Boolean = false,
    @NotNull @ColumnInfo(name = "duracionLluvia")
    var duracionLluvia: Int = 0,
    @NotNull @ColumnInfo(name = "hayCorteAgua")
    var hayCorteAgua: Boolean = false,
    @NotNull @ColumnInfo(name = "duracionCorteAgua")
    var duracionCorteAgua: Int = 0,
    @NotNull @ColumnInfo(name = "incidencia")
    var incidencia: String = ""
)
