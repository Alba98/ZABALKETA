package com.example.zabalketa

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import java.util.*

class RepositorioUsuarios (val miDAO: UsuarioDAO) {
    val listaUsuarios: Flow<List<Usuario>> =miDAO.mostrarTodo()
    val listaUsuarios2: Flow<List<UsuarioClase>> =miDAO.mostrarTodosUsuarios()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertarRegion(miRegion: Region){
        miDAO.insertarRegion(miRegion)
    }

    fun mostrarTodasRegiones(): Flow<List<Region>>{
        return miDAO.mostrarTodasRegiones()
    }

    fun buscarRegionPorId(id:Int):Flow<Region>{
        return miDAO.buscarRegionPorId(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertar(miUsuario: Usuario){
        miDAO.insertar(miUsuario)
    }

    fun buscarPorId(id:Int):Flow<Usuario>{
        return miDAO.buscarPorId(id)
    }

    fun buscarPorUsername(username:String):Flow<Usuario>{
        return miDAO.buscarPorUsername(username)
    }

    @Suppress("RedundatSuspendModifier")
    @WorkerThread
    suspend fun borrar(miUsuario: Usuario){
        miDAO.borrar(miUsuario)
    }

    @Suppress("RedundatSuspendModifier")
    @WorkerThread
    suspend fun modificar(miUsuario: Usuario){
        miDAO.modificar(miUsuario)
    }
}