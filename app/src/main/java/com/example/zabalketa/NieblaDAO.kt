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

    @Query("SELECT n.id, n.fecha, u.username, r.region, n.hayNiebla, n.hayLluvia, n.hayCorteAgua, n.incidencia " +
            "FROM tabla_nieblas AS n " +
            "INNER JOIN tabla_usuarios AS u ON n.idUsuario = u.id "+
            "INNER JOIN tabla_regiones AS r ON u.idRegion = r.id " +
            "ORDER BY fecha ASC")
    fun mostrarTodasNieblas(): Flow<List<NieblaClase>>

    @Query("SELECT n.id, n.fecha, u.username, r.region, n.hayNiebla, n.hayLluvia, n.hayCorteAgua, n.incidencia " +
            "FROM tabla_nieblas AS n " +
            "INNER JOIN tabla_usuarios AS u ON n.idUsuario = u.id "+
            "INNER JOIN tabla_regiones AS r ON u.idRegion = r.id " +
            "WHERE idUsuario = :idUsuario " +
            "ORDER BY fecha ASC")
    fun mostrarTodasNieblasUsuario(idUsuario: Int): Flow<List<NieblaClase>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNiebla(Niebla: Niebla)

    @Transaction
    suspend fun insertNieblaWithCheck(niebla: Niebla) {
        val existingNiebla = getNieblaByFechaAndUsuario(niebla.fecha, niebla.idUsuario)
        if (existingNiebla == null) {
            insertNiebla(niebla)
        } else {
            // Actualizar la entrada existente en lugar de manejar el caso de existencia
            Actualizar(niebla)
        }
    }

    @Query("SELECT * FROM tabla_nieblas WHERE fecha = :fecha AND idUsuario = :idUsuario")
    fun getNieblaByFechaAndUsuario(fecha: String, idUsuario: Int): Niebla?

    /*
    @Query("SELECT * FROM tabla_nieblas WHERE fecha = :fecha")
    fun getNieblaByFecha(fecha: String): Flow<Niebla>
     */

    @Query("DELETE FROM tabla_nieblas")
    fun BorrarTodo()

    @Query("SELECT * FROM tabla_nieblas WHERE id LIKE :id")
    fun BuscarPorID(id: Int): Flow<Niebla>

    @Update
    suspend fun Actualizar(Niebla: Niebla)

    @Delete
    suspend fun Borrar(Niebla: Niebla)
}