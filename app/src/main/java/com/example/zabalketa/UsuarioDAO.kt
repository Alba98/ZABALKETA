package com.example.zabalketa

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface UsuarioDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarRegion(miRegion:Region)

    @Query ("SELECT * FROM tabla_regiones ORDER BY id ASC")
    fun mostrarTodosRegiones() : Flow<List<Region>>

    @Query("SELECT * FROM tabla_regiones where id like :id")
    fun buscarRegionPorId(id:Int):Flow<Region>

    @Query("SELECT u.id, u.username, r.region FROM tabla_regiones as r, tabla_usuarios as u where u.region like r.id")
    fun mostrarTodasUsuarios():Flow<List<UsuarioClase>>

    @Query ("SELECT * FROM tabla_usuarios ORDER BY id ASC")
    fun mostrarTodo() : Flow<List<Usuario>>

    @Query("SELECT * FROM tabla_usuarios where id like :id")
    fun buscarPorId(id:Int):Flow<Usuario>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertar(miUsuario:Usuario)

    @Delete
    suspend fun borrar(miUsuario: Usuario)

    @Update
    suspend fun modificar(miUsuario: Usuario)
}