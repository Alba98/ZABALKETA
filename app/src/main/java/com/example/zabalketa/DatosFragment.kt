package com.example.zabalketa

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.ejercicio6.databinding.FragmentDatosBinding

class DatosFragment : Fragment() {
    private var _binding: FragmentDatosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDatosBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("fragmentoDatos", "ha llegado a dATOS")

        val idPelicula:Int=arguments?.getInt("id") ?:-1
        var miPelicula = Pelicula()

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                if (idPelicula==-1) menuInflater.inflate(R.menu.menu_fragment_datos_insertar,menu)
                else menuInflater.inflate(R.menu.menu_fragment_datos_modificar, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.miGuardar -> {
                        guardar()
                        true
                    }
                    R.id.miModificar -> {
                        modificar(idPelicula)
                        true
                    }
                    R.id.miBorrar -> {
                        borrar(miPelicula)
                        true
                    }
                    else -> false
                }
            }
        },viewLifecycleOwner, Lifecycle.State.RESUMED)

        Log.d("desde donde",findNavController().currentBackStackEntry?.id.toString())
        Log.d("id segundo", R.id.SecondFragment.toString())

        if(idPelicula==-1){
            binding.bBorrar.isEnabled=false
            binding.bModificar.isEnabled=false
            binding.bInsertar.isEnabled=true
        }
        else{
            binding.bBorrar.isEnabled=true
            binding.bModificar.isEnabled=true
            binding.bInsertar.isEnabled=false

            (activity as MainActivity).peliculasVM.BuscarPorID(idPelicula)
            (activity as MainActivity).peliculasVM.pelicula.observe(activity as MainActivity){pelicula ->
                pelicula?.let{
                    miPelicula = pelicula
                    binding.tvTituloDatos.setText(pelicula.titulo)
                    binding.tvGeneroDatos.setText(pelicula.genero)
                    binding.tvEstrenoDatos.setText(pelicula.estreno.toString())
                }
            }
        }

        binding.bInsertar.setOnClickListener {
            guardar()
        }

        binding.bBorrar.setOnClickListener {
            borrar(miPelicula)
        }

        binding.bModificar.setOnClickListener {
            modificar(idPelicula)
        }
        Log.d("idPelicula",idPelicula.toString())
    }

    fun guardar(){
        if(binding.tvTituloDatos.text.isEmpty() or
            binding.tvGeneroDatos.text.isEmpty() or
            binding.tvEstrenoDatos.text.isEmpty()){
            Toast.makeText(activity,"Tienes que insertar todos los datos", Toast.LENGTH_LONG).show()
        }
        else{

            (activity as MainActivity).peliculasVM.Insertar(
                Pelicula(
                    titulo = binding.tvTituloDatos.text.toString(),
                    genero = binding.tvGeneroDatos.text.toString(),
                    estreno = binding.tvEstrenoDatos.text.toString().toInt()
                )
            )
            Toast.makeText(activity,"Pelicula insertada", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_datosFragment_to_SecondFragment)
        }
    }
     fun modificar(idPelicula: Int){
        if(binding.tvTituloDatos.text.isEmpty() or
            binding.tvGeneroDatos.text.isEmpty() or
            binding.tvEstrenoDatos.text.isEmpty()){
            Toast.makeText(activity,"Tienes que insertar todos los datos", Toast.LENGTH_LONG).show()
        }
        else{

            (activity as MainActivity).peliculasVM.Actualizar(
                Pelicula(
                    id = idPelicula,
                    titulo = binding.tvTituloDatos.text.toString(),
                    genero = binding.tvGeneroDatos.text.toString(),
                    estreno = binding.tvEstrenoDatos.text.toString().toInt()
                )
            )

            Toast.makeText(activity,"Pelicula modificada", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_datosFragment_to_SecondFragment)
        }
    }
    fun borrar(miPelicula:Pelicula){
        (activity as MainActivity).peliculasVM.Borrar(miPelicula)
        Toast.makeText(activity,"Pelicula eliminada", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_datosFragment_to_SecondFragment)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        if(id != -1)
            (activity as MainActivity).peliculasVM.pelicula.removeObservers(activity as MainActivity)
    }
}