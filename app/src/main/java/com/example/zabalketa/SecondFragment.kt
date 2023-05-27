package com.example.zabalketa

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zabalketa.databinding.FragmentSecondBinding
import com.google.android.material.appbar.AppBarLayout

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var miRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //(activity as MainActivity).showAppBarLayout()

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_show, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.miStats -> {
                        findNavController().navigate(R.id.action_SecondFragment_to_datosFragment)
                        true
                    }
                    R.id.miPerfil -> {
                        val preferences = (activity as MainActivity)
                            .getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
                        val myEdit = preferences.edit()
                        myEdit.putInt("idUsuario", -1)
                        myEdit.apply()
                        findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
                        true
                    }
                    else -> false
                }
            }
        },viewLifecycleOwner, Lifecycle.State.RESUMED)

        val preferences = (activity as MainActivity)
            .getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        (activity as MainActivity).nieblaVM.mostrarTodasNieblasUsuario(preferences.getInt( "idUsuario", -1))

        //lista de nieblas clase dinamica
        (activity as MainActivity).nieblaVM.datosNieblas2.observe(activity as MainActivity) {
            niebla ->
            niebla?.let { miRecyclerView = binding.rvPosiciones
                miRecyclerView.layoutManager = LinearLayoutManager(activity)
                miRecyclerView.adapter=AdaptadorNiebla(it)
                //Log.d("nieblas",it.count().toString())
            } ?: Toast.makeText(activity,"es null", Toast.LENGTH_LONG).show()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (activity as MainActivity).nieblaVM.datosNieblas2.removeObservers(activity as MainActivity)
    }

}