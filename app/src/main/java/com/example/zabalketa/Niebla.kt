package com.example.zabalketa

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.sql.Date

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import org.json.JSONArray
import org.json.JSONObject

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
    @NotNull @ColumnInfo(name = "franjasDensidades")
    @TypeConverters(FranjaHorariaDensidadListConverter::class)
    var franjasDensidades: List<FranjaHorariaDensidad> = emptyList(),
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

data class FranjaHorariaDensidad(
    @ColumnInfo(name = "idFranjaHoraria")
    var idFranjaHoraria: Int = 0,
    @ColumnInfo(name = "idDensidad")
    var idDensidad: Int = 0
)

class FranjaHorariaDensidadListConverter {
    @TypeConverter
    fun fromList(list: List<FranjaHorariaDensidad>): String {
        val jsonArray = JSONArray()
        for (item in list) {
            val jsonObject = JSONObject()
            jsonObject.put("idFranjaHoraria", item.idFranjaHoraria)
            jsonObject.put("idDensidad", item.idDensidad)
            jsonArray.put(jsonObject)
        }
        return jsonArray.toString()
    }

    @TypeConverter
    fun toList(data: String): List<FranjaHorariaDensidad> {
        val list = mutableListOf<FranjaHorariaDensidad>()
        val jsonArray = JSONArray(data)
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val idFranjaHoraria = jsonObject.getInt("idFranjaHoraria")
            val idDensidad = jsonObject.getInt("idDensidad")
            list.add(FranjaHorariaDensidad(idFranjaHoraria, idDensidad))
        }
        return list
    }
}
