package com.example.zabalketa

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NieblaDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarDensidad(miDensidad:Densidad)

    @Query ("SELECT * FROM tabla_densidades ORDER BY id ASC")
    fun mostrarTodasDensidades() : Flow<List<Densidad>>

    @Query("SELECT * FROM tabla_densidades where id like :id")
    fun buscarDensidadPorId(id:Int):Flow<Densidad>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarFranjaHoraria(miFranjaHoraria:FranjaHoraria)

    @Query ("SELECT * FROM tabla_franjaHorarias ORDER BY id ASC")
    fun mostrarTodasFranjaHorariaes() : Flow<List<FranjaHoraria>>

    @Query("SELECT * FROM tabla_franjaHorarias where id like :id")
    fun buscarFranjaHorariaPorId(id:Int):Flow<FranjaHoraria>

    // @Query("SELECT u.id, u.username, u.clave, r.densidad FROM tabla_densidades as r, tabla_usuarios as u where u.idDensidad like r.id")
    // fun mostrarTodasNieblas():Flow<List<NieblaClase>>


    @Query("SELECT * FROM tabla_nieblas ORDER BY fecha ASC")
    fun MostrarTodas(): Flow<List<Niebla>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun Insertar(Niebla: Niebla)

    @Query("DELETE FROM tabla_nieblas")
    fun BorrarTodo()

    @Query("SELECT * FROM tabla_nieblas WHERE id LIKE :id")
    fun BuscarPorID(id: Int): Flow<Niebla>

    @Update
    suspend fun Actualizar(Niebla: Niebla)

    @Delete
    suspend fun Borrar(Niebla: Niebla)
}