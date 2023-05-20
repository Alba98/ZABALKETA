package com.example.zabalketa

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "tabla_usuarios")
data class Usuario(
    @PrimaryKey (autoGenerate = true)
    var id: Int = 0,
    @NotNull @ColumnInfo (name = "username")
    var username: String = "",
    @NotNull @ColumnInfo (name = "clave")
    var clave: String = "",
    @NotNull @ColumnInfo (name = "region")
    var region: String = "",
) {}