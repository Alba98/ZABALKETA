package com.example.zabalketa

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class NieblaVM(private val miRepositorio: RepositorioNieblas) : ViewModel() {
    var datosNieblas:LiveData<List<Niebla>> = miRepositorio.listaNieblas.asLiveData()
    lateinit var miNiebla:LiveData<Niebla>
    lateinit var listaDensidades: LiveData<List<Densidad>>
    lateinit var miDensidad: LiveData<Densidad>
    lateinit var listaFranjasHorarias: LiveData<List<FranjaHoraria>>
    lateinit var miFranjaHoraria: LiveData<FranjaHoraria>
    var datosNieblas2: LiveData<List<NieblaClase>> = miRepositorio.listaNieblas2.asLiveData()

    fun insertarDensidad(miDensidad: Densidad) =viewModelScope.launch{
        miRepositorio.insertarDensidad(miDensidad)
    }

    fun mostrarTodasDensidades() =viewModelScope.launch {
        listaDensidades= miRepositorio.mostrarTodasDensidades().asLiveData()
    }

    fun buscarDensidadPorId(id:Int) =viewModelScope.launch{
        miDensidad=miRepositorio.buscarDensidadPorId(id).asLiveData()
    }

    fun insertarFranjasHoraria(miFranjasHoraria: FranjaHoraria) =viewModelScope.launch{
        miRepositorio.insertarFranjasHoraria(miFranjasHoraria)
    }

    fun mostrarTodasFranjasHorarias() =viewModelScope.launch {
        listaFranjasHorarias= miRepositorio.mostrarTodasFranjasHorarias().asLiveData()
    }

    fun buscarFranjasHorariaPorId(id:Int) =viewModelScope.launch{
        miFranjaHoraria=miRepositorio.buscarFranjasHorariaPorId(id).asLiveData()
    }

    fun mostrarTodasNieblas() =viewModelScope.launch {
        datosNieblas2= miRepositorio.mostrarTodasNieblas().asLiveData()
    }
    fun mostrarTodasNieblasUsuario(idUsuario: Int) =viewModelScope.launch {
        if(idUsuario != -1)
            datosNieblas2= miRepositorio.mostrarTodasNieblasUsuario(idUsuario).asLiveData()
        else
            datosNieblas2= miRepositorio.mostrarTodasNieblas().asLiveData()
    }
    fun mostrarTodas() =viewModelScope.launch {
        datosNieblas= miRepositorio.mostrarTodas().asLiveData()
    }
    fun insertNiebla(miNiebla: Niebla) = viewModelScope.launch {
        miRepositorio.insertNiebla(miNiebla)
    }

    fun insertNieblaWithCheck(miNiebla: Niebla) = viewModelScope.launch {
        miRepositorio.insertNieblaWithCheck(miNiebla)
    }

    fun buscarPorID(id:Int) = viewModelScope.launch  {
        miNiebla = miRepositorio.BuscarPorID(id).asLiveData()
    }

    fun borrar(miNiebla: Niebla) = viewModelScope.launch  {
        miRepositorio.Borrar(miNiebla)
    }

    fun actualizar(miNiebla: Niebla) = viewModelScope.launch  {
        miRepositorio.Actualizar(miNiebla)
    }
}
class WordViewModelFactory(private val repositorioNieblas: RepositorioNieblas): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NieblaVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NieblaVM(repositorioNieblas) as T
        }
        throw IllegalArgumentException("ViewModel class desconocida")
    }
}