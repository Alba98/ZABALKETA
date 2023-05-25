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
    suspend fun insertarFranjaHoraria(miFranjasHoraria:FranjaHoraria)

    @Query ("SELECT * FROM tabla_franjasHorarias ORDER BY id ASC")
    fun mostrarTodasFranjasHorarias() : Flow<List<FranjaHoraria>>

    @Query("SELECT * FROM tabla_franjasHorarias where id like :id")
    fun buscarFranjasHorariaPorId(id:Int):Flow<FranjaHoraria>

    @Query("SELECT * FROM tabla_nieblas ORDER BY fecha ASC")
    fun MostrarTodas(): Flow<List<Niebla>>

    @Query("SELECT t.id, t.fecha, r.region, d.densidad FROM tabla_regiones as r, tabla_nieblas as t, tabla_densidades as d where t.idRegion like r.id and t.idDensidad like d.id")
    fun MostrarTodasNiebla():Flow<List<NieblaClase>>


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