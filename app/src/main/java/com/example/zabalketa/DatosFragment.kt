package com.example.zabalketa

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.zabalketa.databinding.FragmentDatosBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class DatosFragment : Fragment() {
    private var _binding: FragmentDatosBinding? = null

    var idNiebla:Int=-1
    var totalDensidades:Int=-1
    var totalRegiones:Int=-1

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

        (activity as MainActivity).usuarioVM.mostrarTodasRegiones()
        (activity as MainActivity).usuarioVM.listaRegiones.observe(activity as MainActivity){
            binding.sRegion.adapter = AdaptadorRegion(activity as MainActivity, it)
            totalDensidades=it.count()
        }

        (activity as MainActivity).nieblaVM.mostrarTodasDensidades()
        (activity as MainActivity).nieblaVM.listaDensidades.observe(activity as MainActivity){
            binding.sDensidad.adapter = AdaptadorDensidad(activity as MainActivity, it)
            totalDensidades=it.count()
        }

        binding.apply {
            // Obtener la fecha actual
            val currentDate = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(currentDate.time)

            // Establecer la fecha actual en el EditText
            tvFecha.setText(formattedDate)

            tvFecha.setOnClickListener {
                // Obtener el rango de fechas permitido (hasta la fecha actual)
                val currentTimestamp = currentDate.timeInMillis
                val constraintsBuilder = CalendarConstraints.Builder()
                    .setValidator(DateValidatorPointBackward.before(currentTimestamp))

                // Crear el selector de fechas y aplicar las restricciones
                val builder = MaterialDatePicker.Builder.datePicker()
                    .setCalendarConstraints(constraintsBuilder.build())
                val datePicker = builder.build()

                // Establecer el listener cuando se selecciona una fecha
                datePicker.addOnPositiveButtonClickListener { selection ->
                    // Crear objeto Calendar y establecer la fecha seleccionada
                    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                    calendar.timeInMillis = selection

                    // Formatear la fecha seleccionada y establecerla en el EditText
                    val formattedDate = dateFormat.format(calendar.time)
                    tvFecha.setText(formattedDate)
                }

                datePicker.show(parentFragmentManager, "MyTAG")
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

        }

        idNiebla=arguments?.getInt("id") ?:-1
        var miNiebla = Niebla()
        if(idNiebla==-1){
            binding.bBorrar.isEnabled=false
            binding.bModificar.isEnabled=false
            binding.bInsertar.isEnabled=true
        }
        else{
            binding.bBorrar.isEnabled=true
            binding.bModificar.isEnabled=true
            binding.bInsertar.isEnabled=false

            (activity as MainActivity).nieblaVM.BuscarPorID(idNiebla)
            (activity as MainActivity).nieblaVM.miNiebla.observe(activity as MainActivity){ niebla->
                miNiebla=niebla
               // binding.tvTituloDatos.setText(niebla.titulo)
                setearSpiner(niebla.idDensidad)
                // binding.tvEstrenoDatos.setText(niebla.estreno.toString())
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

    fun setearSpiner(idDensidad:Int){
        if(totalDensidades!=-1){
            for (i in 0 until totalDensidades) {
                val option = binding.sDensidad.adapter.getItemId(i).toInt()
                if (option == idDensidad) {
                    binding.sDensidad.setSelection(i)
                    break
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}