package com.example.zabalketa

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = arrayOf(Usuario::class,Region::class, Niebla::class), version = 1, exportSchema = false)
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
                    "my_app_database"
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
                    Usuario(username = "albatxuL", clave = "test", idRegion = "1")
                )
                // Inserta las películas iniciales en la base de datos
                val viewModelScope = CoroutineScope(Dispatchers.IO)
                viewModelScope.launch {
                    // Realizar la operación en segundo plano (p. ej., poblar la base de datos)
                    regionesIniciales.forEach { region -> INSTANCE?.miUsuarioDAO()?.insertarRegion(region) }
                    usuariosIniciales.forEach { usuario -> INSTANCE?.miUsuarioDAO()?.insertar(usuario) }
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