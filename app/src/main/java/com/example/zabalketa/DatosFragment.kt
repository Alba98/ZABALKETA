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
import com.example.zabalketa.databinding.FragmentDatosBinding

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

        binding.apply {
            tvFecha.setOnClickListener {
                // create new instance of DatePickerFragment
                val datePickerFragment = DatePickerFragment()
                val supportFragmentManager = requireActivity().supportFragmentManager

                // we have to implement setFragmentResultListener
                supportFragmentManager.setFragmentResultListener(
                    "REQUEST_KEY",
                    viewLifecycleOwner
                ) { resultKey, bundle ->
                    if (resultKey == "REQUEST_KEY") {
                        val date = bundle.getString("SELECTED_DATE")
                        // tvFecha.text = date
                        tvFecha.setText(date)
                    }
                }

                // show
                datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
            }
        }


        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_edit, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.miModificar -> {
                        findNavController().navigate(R.id.action_datosFragment_to_SecondFragment)
                        true
                    }
                    else -> false
                }
            }
        },viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
/*
    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(FragmentDatosBinding, "datePicker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        //tvFecha.setText("Has seleccionado el $day del $month del año $year")
    }
    */
}