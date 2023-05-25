package com.example.zabalketa

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Switch
import android.widget.Toast
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
            totalRegiones=it.count()
        }

        (activity as MainActivity).nieblaVM.mostrarTodasDensidades()
        (activity as MainActivity).nieblaVM.listaDensidades.observe(activity as MainActivity){
            binding.sDensidad.adapter = AdaptadorDensidad(activity as MainActivity, it)
            totalDensidades=it.count()
        }

        (activity as MainActivity).nieblaVM.mostrarTodasFranjasHorarias()
        (activity as MainActivity).nieblaVM.listaFranjasHorarias.observe(activity as MainActivity) { franjasHorarias ->
            val radioGroup = binding.rgFranjasHorarias

            var rowLayout: LinearLayout? = null // Layout para cada fila de RadioButton
            var count = 0 // Contador para determinar cuándo se deben crear nuevas filas

            for (franjaHoraria in franjasHorarias) {
                if (count % 2 == 0) {
                    // Crear un nuevo LinearLayout para cada par de RadioButton
                    rowLayout = LinearLayout(activity as MainActivity)
                    rowLayout.orientation = LinearLayout.HORIZONTAL

                    val layoutParams = RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.MATCH_PARENT,
                        RadioGroup.LayoutParams.WRAP_CONTENT
                    )
                    radioGroup.addView(rowLayout, layoutParams)
                }

                val radioButton = RadioButton(activity as MainActivity)
                radioButton.id = View.generateViewId()
                radioButton.text = franjaHoraria.franja

                val layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f
                )
                layoutParams.marginStart = resources.getDimensionPixelSize(R.dimen.radio_button_margin_start)
                layoutParams.marginEnd = resources.getDimensionPixelSize(R.dimen.radio_button_margin_end)

                rowLayout?.addView(radioButton, layoutParams)

                count++
            }
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
                    val formatDate = dateFormat.format(calendar.time)
                    tvFecha.setText(formatDate)
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

            bNiebla.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    sDensidad.visibility = View.VISIBLE
                    rgFranjasHorarias.visibility = View.VISIBLE
                } else {
                    sDensidad.visibility = View.GONE
                    rgFranjasHorarias.visibility = View.GONE
                }
            }

            bLluvia.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    sbDuracionLluvia.visibility = View.VISIBLE
                } else {
                    sbDuracionLluvia.visibility = View.GONE
                }
            }

            bCorteAgua.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    sbDuracionCorte.visibility = View.VISIBLE
                } else {
                    sbDuracionCorte.visibility = View.GONE
                }
            }

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

            (activity as MainActivity).nieblaVM.buscarPorID(idNiebla)
            (activity as MainActivity).nieblaVM.miNiebla.observe(activity as MainActivity) { niebla ->
                miNiebla = niebla
                binding.tvFecha.setText(niebla.fecha)
                binding.bNiebla.isChecked = niebla.hayNiebla
                setearSpiner(niebla.idDensidad)
                binding.bLluvia.isChecked = niebla.hayLluvia
                binding.sbDuracionLluvia.progress = niebla.duracionLluvia
                binding.bCorteAgua.isChecked = niebla.hayCorteAgua
                binding.sbDuracionCorte.progress = niebla.duracionCorteAgua
                binding.tvIncidencias.setText(niebla.incidencia)
            }
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_edit, menu)
                // Add menu items here
                if (idNiebla==-1) menuInflater.inflate(R.menu.menu_insert,menu)
                else menuInflater.inflate(R.menu.menu_edit, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.miGuardar -> {
                        if(validarContenido()) guardar()
                        true
                    }
                    R.id.miModificar -> {
                        if(validarContenido()) modificar(idNiebla)
                        true
                    }
                    R.id.miBorrar -> {
                        borrar(miNiebla)
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
            today = getDate?.let { formatter.parse(it) }
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

    fun guardar(){
        (activity as MainActivity).nieblaVM.insertar(Niebla(
            fecha = binding.tvFecha.text.toString(),
            idUsuario = 1,
            hayNiebla = binding.bNiebla.isChecked,
            idDensidad = binding.sDensidad.selectedItemId.toInt(),
            idFranja = binding.rgFranjasHorarias.checkedRadioButtonId,
            hayLluvia = binding.bLluvia.isChecked,
            duracionLluvia = binding.sbDuracionLluvia.progress,
            hayCorteAgua = binding.bCorteAgua.isChecked,
            duracionCorteAgua = binding.sbDuracionCorte.progress,
            incidencia = binding.tvIncidencias.text.toString()
        ))
        Toast.makeText(activity,"Pelicula insertada", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_datosFragment_to_SecondFragment)

    }

    fun modificar(idPelicula: Int){
        (activity as MainActivity).nieblaVM.actualizar(Niebla(
            idPelicula,
            fecha = binding.tvFecha.text.toString(),
            idUsuario = 1,
            hayNiebla = binding.bNiebla.isChecked,
            idDensidad = binding.sDensidad.selectedItemId.toInt(),
            idFranja = binding.rgFranjasHorarias.checkedRadioButtonId,
            hayLluvia = binding.bLluvia.isChecked,
            duracionLluvia = binding.sbDuracionLluvia.progress,
            hayCorteAgua = binding.bCorteAgua.isChecked,
            duracionCorteAgua = binding.sbDuracionCorte.progress,
            incidencia = binding.tvIncidencias.text.toString()
        ))
        Toast.makeText(activity,"Pelicula modificada", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_datosFragment_to_SecondFragment)
    }

    fun borrar(miNiebla:Niebla){
        (activity as MainActivity).nieblaVM.borrar(miNiebla)
        Toast.makeText(activity,"Pelicula eliminada", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_datosFragment_to_SecondFragment)
    }

    fun validarContenido():Boolean{
        // if (binding.tvFecha.text.isEmpty() || binding.sRegion.selectedItemPosition == 0) {
        //     Toast.makeText(activity, "Debe seleccionar una fecha y una región", Toast.LENGTH_LONG).show()
        //     return false
        // }

        // val fechaSeleccionada = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(binding.tvFecha.text.toString())
        // val fechaHoy = Calendar.getInstance().time
        //
        // if (fechaSeleccionada != null && fechaSeleccionada.before(fechaHoy)) {
        //     Toast.makeText(activity, "La fecha seleccionada debe ser igual o posterior a hoy", Toast.LENGTH_LONG).show()
        //     return false
        // }

        return true
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}