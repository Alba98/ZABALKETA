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

    /*
     @Query("SELECT n.id, n.fecha, u.username, r.region, n.hayNiebla, d.densidad, f.franja, n.hayLluvia, n.duracionLluvia, n.hayCorteAgua, n.duracionCorteAgua, n.incidencia " +
            "FROM tabla_nieblas AS n " +
            "INNER JOIN tabla_usuarios AS u ON n.idUsuario = u.id "+
            "INNER JOIN tabla_regiones AS r ON u.idRegion = r.id " +
            "INNER JOIN tabla_densidades AS d ON n.idDensidad = d.id " +
            "INNER JOIN tabla_franjasHorarias AS f ON n.idFranja = f.id ")
     */
    @Query("SELECT n.id, n.fecha, u.username, r.region, n.hayNiebla, d.densidad, n.hayLluvia, n.duracionLluvia, n.hayCorteAgua, n.duracionCorteAgua, n.incidencia " +
            "FROM tabla_nieblas AS n " +
            "INNER JOIN tabla_usuarios AS u ON n.idUsuario = u.id "+
            "INNER JOIN tabla_regiones AS r ON u.idRegion = r.id " +
            "INNER JOIN tabla_densidades AS d ON n.idDensidad = d.id " )
    fun MostrarTodasNieblas(): Flow<List<NieblaClase>>

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