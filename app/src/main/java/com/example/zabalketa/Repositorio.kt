package com.example.zabalketa

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class Repositorio(val miDao: NieblaDAO) {
    val listaNieblas: Flow<List<Niebla>> = miDao.MostrarTodas()
    val listaNieblas2: Flow<List<NieblaClase>> = miDao.MostrarTodasNiebla()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun Insertar(miNiebla: Niebla) {
        miDao.Insertar(miNiebla)
    }

    fun BuscarPorID(id:Int): Flow<Niebla> {
        return miDao.BuscarPorID(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun Borrar(miNiebla: Niebla) {
        miDao.Borrar(miNiebla)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun Actualizar(miNiebla: Niebla) {
        miDao.Actualizar(miNiebla)
    }

}