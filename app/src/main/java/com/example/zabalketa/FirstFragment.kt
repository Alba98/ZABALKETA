package com.example.zabalketa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.zabalketa.databinding.FragmentFirstBinding
//import au.com.bytecode.opencsv.CSVReader
import com.opencsv.CSVReader
import java.io.InputStreamReader

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //var miUsuario = Usuario()
        binding.loginbtn.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()

            // Leer el archivo CSV y comprobar los datos
            val fileReader = CSVReader(InputStreamReader(resources.assets.open("usuarios.txt")))
            val csvData = fileReader.readAll()

            var isLoginSuccessful = false

            for (row in csvData) {
                val csvUsername = row[0]
                val csvPassword = row[1]

                if (csvUsername == username && csvPassword == password) {
                    isLoginSuccessful = true
                    break
                }
            }

            if (isLoginSuccessful) {
                Toast.makeText(activity, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            } else {
                Toast.makeText(activity, "Nombre de usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
/*
        binding.loginbtn.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)

            // (activity as MainActivity).UsuarioVM.buscarPorUsername(
            //     binding.username.toString()
            // )
            // (activity as MainActivity).UsuarioVM.miUsuario.observe(activity as MainActivity){ usuario->
            //     miUsuario=usuario
            // }

            // Toast.makeText(activity,"Bienvenid@" + miUsuario, Toast.LENGTH_LONG).show()

            // if(miUsuario.clave ==  binding.password.toString()) {
            //     Toast.makeText(activity,"Bienvenid@", Toast.LENGTH_LONG).show()
            //      findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            // }
        }
 */
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        //(activity as MainActivity).UsuarioVM.miUsuario.removeObservers(activity as MainActivity)
    }
}