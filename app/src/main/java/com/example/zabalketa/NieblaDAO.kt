package com.example.zabalketa

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NieblaDAO {
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