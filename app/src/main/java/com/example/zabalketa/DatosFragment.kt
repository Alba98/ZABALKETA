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
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

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
            // Obtener la fecha actual
            val currentDate = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(currentDate.time)
            var formattedDate_ : Date?= null

            // Establecer la fecha actual en el EditText
            tvFecha.setText(formattedDate)

            binding.tvFecha.setOnClickListener {
                // Create the date picker builder and set the title
                val builder = MaterialDatePicker.Builder.datePicker()
                // create the date picker
                val datePicker = builder.build()
                // set listener when date is selected
                datePicker.addOnPositiveButtonClickListener {
                    // Create calendar object and set the date to be that returned from selection
                    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                    calendar.time = Date(it)
                    formattedDate_ =  convertStringToData(calendar.get(Calendar.DAY_OF_MONTH).toString() + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR))
                    binding.tvFecha.setText( formattedDate_.toString())
                }

                datePicker.show(getParentFragmentManager(), "MyTAG")
            }

            /*tvFecha.setOnClickListener {
                val datePickerFragment = DatePickerFragment()
                val supportFragmentManager = requireActivity().supportFragmentManager

                supportFragmentManager.setFragmentResultListener(
                    "REQUEST_KEY",
                    viewLifecycleOwner
                ) { resultKey, bundle ->
                    if (resultKey == "REQUEST_KEY") {
                        val date = bundle.getString("SELECTED_DATE")
                        tvFecha.setText(date) // Actualiza el texto del EditText con la fecha seleccionada
                        Toast.makeText(activity,"Actualizar fecha", Toast.LENGTH_LONG).show()
                    }
                }

                datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
            }*/

            /*
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
             */
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

    fun convertStringToData(getDate: String?): Date? {
        var today: Date?=null
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        try {
            today = formatter.parse(getDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return today
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