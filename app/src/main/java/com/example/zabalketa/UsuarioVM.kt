package com.example.zabalketa

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.util.*

class UsuarioVM(private val miRepositorio: RepositorioUsuarios): ViewModel() {
    val listaUsuarios: LiveData<List<Usuario>> = miRepositorio.listaUsuarios.asLiveData()
    lateinit var miUsuario: LiveData<Usuario>
  //  lateinit var miUsuarioClase: LiveData<UsuarioClase>
    lateinit var listaRegiones: LiveData<List<Region>>
    lateinit var miRegion: LiveData<Region>
    val listaUsuarios2: LiveData<List<UsuarioClase>> = miRepositorio.listaUsuarios2.asLiveData()


    fun insertarRegion(miRegion: Region) =viewModelScope.launch{
        miRepositorio.insertarRegion(miRegion)
    }

    fun mostrarTodasRegiones() =viewModelScope.launch {
        listaRegiones= miRepositorio.mostrarTodasRegiones().asLiveData()
    }

    fun buscarRegionPorId(id:Int) =viewModelScope.launch{
        miRegion=miRepositorio.buscarRegionPorId(id).asLiveData()
    }

    fun insertar(miUsuario: Usuario) =viewModelScope.launch{
        miRepositorio.insertar(miUsuario)
    }

    fun borrar(miUsuario: Usuario) =viewModelScope.launch{
        miRepositorio.borrar(miUsuario)
    }

    fun modificar(miUsuario: Usuario) =viewModelScope.launch{
        miRepositorio.modificar(miUsuario)
    }

    fun buscarPorId(id:Int) =viewModelScope.launch{
        miUsuario=miRepositorio.buscarPorId(id).asLiveData()
    }

    fun buscarPorUsername(username:String) =viewModelScope.launch{
        miUsuario=miRepositorio.buscarPorUsername(username).asLiveData()
    }
}
class UsuarioVMFactory(private val miRepositorio: RepositorioUsuarios):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsuarioVM::class.java)){
            @Suppress("UNCHECKED_CAST")
            return UsuarioVM(miRepositorio) as T
        }
        throw IllegalArgumentException("ViewModel class desconocida")
    }
}

