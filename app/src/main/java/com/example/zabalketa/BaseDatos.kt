package com.example.zabalketa

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = arrayOf(Usuario::class,Region::class, Niebla::class, Densidad::class, FranjaHoraria::class), version = 1, exportSchema = false)
abstract class BaseDatos:RoomDatabase() {
    abstract fun miUsuarioDAO():UsuarioDAO
    abstract fun miNieblaDAO():NieblaDAO
    companion object{
        @Volatile
        private var INSTANCE:BaseDatos?=null

        fun getDatabase(context:Context):BaseDatos{
            return INSTANCE?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    BaseDatos::class.java,
                    "zabalketa_database"
                )
                    .addCallback(roomCallback)
                    .build().also { INSTANCE = it }
            }
        }

        // Callback que se ejecuta cuando se crea la base de datos
        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // Insertar los datos iniciales
                val regionesIniciales = listOf(
                    Region(region = "VELADERO"),
                    Region(region="SIVINGALITO"),
                    Region(region="PICUTA"),
                    Region(region = "CHOQUEMARCA")
                )
                val usuariosIniciales = listOf(
                    Usuario(username = "albatxuL", clave = "test", idRegion = 1)
                )
                val densidadesIniciales = listOf(
                    Densidad(densidad = "Intensa"),
                    Densidad(densidad="Normal"),
                    Densidad(densidad="Poco intensa")
                )
                val franjaHorariaIniciales = listOf(
                    FranjaHoraria(franja = "6:00 - 9:00"),
                    FranjaHoraria(franja = "9:00 - 12:00"),
                    FranjaHoraria(franja = "12:00 - 14:00"),
                    FranjaHoraria(franja = "14:00 - 18:00"),
                    FranjaHoraria(franja = "18:00 - 21:00"),
                    FranjaHoraria(franja = "21:00 - 24:00"),
                    FranjaHoraria(franja = "24:00 - 6:00")
                )
                // Inserta las películas iniciales en la base de datos
                val viewModelScope = CoroutineScope(Dispatchers.IO)
                viewModelScope.launch {
                    // Realizar la operación en segundo plano (p. ej., poblar la base de datos)
                    regionesIniciales.forEach { region -> INSTANCE?.miUsuarioDAO()?.insertarRegion(region) }
                    usuariosIniciales.forEach { usuario -> INSTANCE?.miUsuarioDAO()?.insertar(usuario) }

                    densidadesIniciales.forEach { densidad -> INSTANCE?.miNieblaDAO()?.insertarDensidad(densidad) }
                    franjaHorariaIniciales.forEach { franja -> INSTANCE?.miNieblaDAO()?.insertarFranjaHoraria(franja) }
                }
            }
        }
    }
}

/*
@Database(entities = arrayOf(Niebla::class), version = 1, exportSchema = false)
abstract class BaseDatos : RoomDatabase() {
    abstract fun miDAO() : NieblaDAO
    companion object {
        @Volatile
        private var INSTANCE: BaseDatos?=null

        fun getDatabase(context: Context): BaseDatos {
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BaseDatos::class.java,
                    "Nieblas_database"
                ).build()
                INSTANCE = instance
                //return instance
                instance
            }
        }
    }
}
 */