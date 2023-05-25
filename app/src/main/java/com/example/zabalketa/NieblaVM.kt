package com.example.zabalketa

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class NieblaVM(private val repositorio: Repositorio) : ViewModel() {
    val datosNieblas:LiveData<List<Niebla>> = repositorio.listaNieblas.asLiveData()
    lateinit var miNiebla:LiveData<Niebla>
    lateinit var listaDensidades: LiveData<List<Densidad>>
    lateinit var miDensidad: LiveData<Densidad>
    val datosNieblas2: LiveData<List<NieblaClase>> = repositorio.listaNieblas2.asLiveData()

    fun insertarDensidad(miDensidad: Densidad) =viewModelScope.launch{
        repositorio.insertarDensidad(miDensidad)
    }

    fun mostrarTodasDensidades() =viewModelScope.launch {
        listaDensidades= repositorio.mostrarTodasDensidades().asLiveData()
    }

    fun buscarDensidadPorId(id:Int) =viewModelScope.launch{
        miDensidad=repositorio.buscarDensidadPorId(id).asLiveData()
    }


    fun Insertar(miNiebla: Niebla) = viewModelScope.launch {
        repositorio.Insertar(miNiebla)
    }

    fun BuscarPorID(id:Int) = viewModelScope.launch  {
        miNiebla = repositorio.BuscarPorID(id).asLiveData()
    }

    fun Borrar(miNiebla: Niebla) = viewModelScope.launch  {
        repositorio.Borrar(miNiebla)
    }

    fun Actualizar(miNiebla: Niebla) = viewModelScope.launch  {
        repositorio.Actualizar(miNiebla)
    }
}
class WordViewModelFactory(private val repositorio: Repositorio): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NieblaVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NieblaVM(repositorio) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}