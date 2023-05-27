package com.example.zabalketa

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(
    tableName = "tabla_usuarios",
    indices = [Index(value = ["username"], unique = true)]
)
data class Usuario(
    @PrimaryKey (autoGenerate = true)
    var id: Int = 0,
    @NotNull @ColumnInfo (name = "username")
    var username: String = "",
    @NotNull @ColumnInfo (name = "clave")
    var clave: String = "",
    @NotNull @ColumnInfo (name = "idRegion")
    var idRegion: Int = 0,
) {}

/*
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index
import org.jetbrains.annotations.NotNull

@Entity(
    tableName = "tabla_usuarios",
    indices = [Index(value = ["username"], unique = true)]
)
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @NotNull
    @ColumnInfo(name = "username")
    var username: String = "",
    @NotNull
    @ColumnInfo(name = "clave")
    var clave: String = "",
    @NotNull
    @ColumnInfo(name = "idRegion")
    var idRegion: Int = 0
)
 */