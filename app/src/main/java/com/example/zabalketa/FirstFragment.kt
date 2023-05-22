package com.example.zabalketa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.zabalketa.databinding.FragmentFirstBinding

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
        var miUsuario = Usuario()
        binding.loginbtn.setOnClickListener {
            //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)

            (activity as MainActivity).UsuarioVM.buscarPorUsername(
                binding.username.text.toString()
            )
            (activity as MainActivity).UsuarioVM.miUsuario.observe(activity as MainActivity){ usuario->
                miUsuario=usuario
            }

            Toast.makeText(activity, miUsuario.toString(), Toast.LENGTH_LONG).show()

            if(miUsuario.clave ==  binding.password.toString()) {
                Toast.makeText(activity,"Bienvenid@", Toast.LENGTH_LONG).show()
                 findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        //(activity as MainActivity).UsuarioVM.miUsuario.removeObservers(activity as MainActivity)
    }
}