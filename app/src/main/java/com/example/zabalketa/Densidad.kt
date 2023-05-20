package com.example.zabalketa

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabla_densidades")
data class Densidad(
    @PrimaryKey(autoGenerate = true) var id:Int=0,
    @NonNull @ColumnInfo(name="densidad") val densidad:String="") {}