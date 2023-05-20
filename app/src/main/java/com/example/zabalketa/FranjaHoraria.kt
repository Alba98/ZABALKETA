package com.example.zabalketa

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabla_franjaHorarias")
data class FranjaHoraria(
    @PrimaryKey(autoGenerate = true) var id:Int=0,
    @NonNull @ColumnInfo(name="franja") val franja:String="") {}