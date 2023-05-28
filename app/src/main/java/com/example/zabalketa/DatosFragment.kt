package com.example.zabalketa

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.zabalketa.databinding.FragmentDatosBinding
import com.google.android.material.appbar.AppBarLayout
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

        //(activity as MainActivity).showAppBarLayout()

        idNiebla = arguments?.getInt("id") ?:-1

        (activity as MainActivity).usuarioVM.mostrarTodasRegiones()
        (activity as MainActivity).usuarioVM.listaRegiones.observe(activity as MainActivity){
            binding.sRegion.adapter = AdaptadorRegion(activity as MainActivity, it)
            totalRegiones=it.count()
        }
        binding.sRegion.setEnabled(false); //desactivar

        (activity as MainActivity).nieblaVM.mostrarTodasDensidades()
        (activity as MainActivity).nieblaVM.listaDensidades.observe(activity as MainActivity){
            //binding.sDensidad.adapter = AdaptadorDensidad(activity as MainActivity, it)
            totalDensidades=it.count()
        }
/*
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
*/

        (activity as MainActivity).nieblaVM.mostrarTodasDensidades()
        (activity as MainActivity).nieblaVM.mostrarTodasFranjasHorarias()
        (activity as MainActivity).nieblaVM.listaFranjasHorarias.observe(activity as MainActivity) { franjasHorarias ->
            val linearLayout = binding.lFranjasHorarias // Cambia el tipo de vista a LinearLayout

            linearLayout.removeAllViews() // Elimina las vistas anteriores antes de agregar las nuevas

            for (franjaHoraria in franjasHorarias) {
                // Crear un LinearLayout para cada fila
                val rowLayout = LinearLayout(activity as MainActivity)
                rowLayout.orientation = LinearLayout.HORIZONTAL

                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams.marginStart = resources.getDimensionPixelSize(R.dimen.radio_button_margin_start)
                layoutParams.marginEnd = resources.getDimensionPixelSize(R.dimen.radio_button_margin_end)

                linearLayout.addView(rowLayout, layoutParams)

                // Agregar TextView para mostrar la hora de la franja horaria
                val textView = TextView(activity as MainActivity)
                textView.text = franjaHoraria.franja

                val textViewLayoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                textViewLayoutParams.weight = 1.0f

                rowLayout.addView(textView, textViewLayoutParams)

                // Agregar Spinner para seleccionar la densidad
                val spinner = Spinner(activity as MainActivity)
                val containerLayout = LinearLayout(activity as MainActivity)
                containerLayout.orientation = LinearLayout.HORIZONTAL

                (activity as MainActivity).nieblaVM.listaDensidades.observe(activity as MainActivity){
                    spinner.adapter = AdaptadorDensidad(activity as MainActivity, it)
                }
                spinner.setSelection(0) // Establece la selección predeterminada del Spinner

                val spinnerLayoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                spinnerLayoutParams.marginStart = resources.getDimensionPixelSize(R.dimen.spinner_margin_start)
                spinnerLayoutParams.marginEnd = resources.getDimensionPixelSize(R.dimen.spinner_margin_end)

                containerLayout.addView(spinner, spinnerLayoutParams)
                rowLayout.addView(containerLayout)
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

            bNiebla.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    //sDensidad.visibility = View.VISIBLE
                    lFranjasHorarias.visibility = View.VISIBLE
                } else {
                    //sDensidad.visibility = View.GONE
                    lFranjasHorarias.visibility = View.GONE
                }
            }

            bLluvia.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    sbDuracionLluvia.visibility = View.VISIBLE
                    tvDuracionLluviaSeleccionada.visibility = View.VISIBLE
                } else {
                    sbDuracionLluvia.visibility = View.GONE
                    tvDuracionLluviaSeleccionada.visibility = View.GONE
                }
            }

            bCorteAgua.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    sbDuracionCorte.visibility = View.VISIBLE
                    tvDuracionCorteSeleccionada.visibility = View.VISIBLE
                } else {
                    sbDuracionCorte.visibility = View.GONE
                    tvDuracionCorteSeleccionada.visibility = View.GONE
                }
            }

            sbDuracionLluvia.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    val horas = progress / 60
                    val minutos = progress % 60
                    tvDuracionLluviaSeleccionada.text = "Duración corte: $horas horas $minutos minutos"
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    // No se necesita implementación adicional en este ejemplo
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    // No se necesita implementación adicional en este ejemplo
                }
            })

            sbDuracionCorte.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    val horas = progress / 60
                    val minutos = progress % 60
                    tvDuracionCorteSeleccionada.text = "Duración corte: $horas horas $minutos minutos"
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    // No se necesita implementación adicional en este ejemplo
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    // No se necesita implementación adicional en este ejemplo
                }
            })
        }

        idNiebla=arguments?.getInt("id") ?:-1
        var miNiebla = Niebla()
        if(idNiebla==-1){
            binding.bBorrar.isEnabled=false
            binding.bModificar.isEnabled=false
            binding.bInsertar.isEnabled=true

            //pillar del shared Preferences
            val preferences = (activity as MainActivity)
                .getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
            if(preferences.getInt( "idUsuario", -1) != -1) {
                // Log.d("idUsuario", preferences.getInt( "idUsuario", -1).toString())
                (activity as MainActivity).usuarioVM.buscarPorId(preferences.getInt( "idUsuario", -1))
                (activity as MainActivity).usuarioVM.miUsuario.observe(activity as MainActivity){
                    it?.let{
                        Toast.makeText(activity, it.toString(), Toast.LENGTH_LONG).show()
                        setearSpinerRegion(it.idRegion)
                    } ?: Toast.makeText(activity, "usuario igual a null", Toast.LENGTH_LONG).show()
                }
            }

        }
        else{
            binding.bBorrar.isEnabled=true
            binding.bModificar.isEnabled=true
            binding.bInsertar.isEnabled=false

            (activity as MainActivity).nieblaVM.buscarPorID(idNiebla)
            (activity as MainActivity).nieblaVM.miNiebla.observe(activity as MainActivity) { niebla ->
                niebla?.let {
                    miNiebla = niebla
                    binding.tvFecha.setText(niebla.fecha)

                    (activity as MainActivity).usuarioVM.buscarPorId(niebla.idUsuario)
                    (activity as MainActivity).usuarioVM.miUsuario.value?.let {
                        setearSpinerRegion(it.idRegion)
                    }

                    binding.bNiebla.isChecked = niebla.hayNiebla

                    // Establecer los valores seleccionados en los Spinners
                    for (i in 0 until binding.lFranjasHorarias.childCount) {
                        val rowLayout = binding.lFranjasHorarias.getChildAt(i) as? LinearLayout
                        val containerLayout = rowLayout?.getChildAt(1) as? LinearLayout
                        val spinner = containerLayout?.getChildAt(0) as? Spinner

                        if (spinner != null) {
                            val franjaHorariaDensidad = niebla.franjasDensidades.find { it.idFranjaHoraria == i }
                            val selectedDensidadId = franjaHorariaDensidad?.idDensidad

                            // Obtener la posición correspondiente al ID de la densidad seleccionada
                            val position = selectedDensidadId?.let { idDensidad ->
                                (spinner.adapter as? AdaptadorDensidad)?.getPositionById(
                                    idDensidad
                                )
                            }

                            // Establecer la selección en el spinner
                            if (position != null) {
                                spinner.setSelection(position)
                            }
                        }
                    }

                    binding.bLluvia.isChecked = niebla.hayLluvia
                    binding.sbDuracionLluvia.progress = niebla.duracionLluvia

                    binding.bCorteAgua.isChecked = niebla.hayCorteAgua
                    binding.sbDuracionCorte.progress = niebla.duracionCorteAgua

                    binding.tvIncidencias.setText(niebla.incidencia)
                }
            }
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
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

    private fun setearSpinerRegion(idRegion: Int) {
        if(totalRegiones!=-1) {
            for (i in 0 until totalRegiones) {
                val option = binding.sRegion.adapter.getItemId(i).toInt()
                if (option == idRegion) {
                    binding.sRegion.setSelection(i)
                    break
                }
            }
        }
    }

    fun guardar() {
        val preferences = (activity as MainActivity).getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        if (preferences.getInt("idUsuario", -1) != -1) {
            val selectedFranjasDensidades = mutableListOf<FranjaHorariaDensidad>()

            for (i in 0 until binding.lFranjasHorarias.childCount) {
                val rowLayout = binding.lFranjasHorarias.getChildAt(i) as? LinearLayout
                val containerLayout = rowLayout?.getChildAt(1) as? LinearLayout
                val spinner = containerLayout?.getChildAt(0) as? Spinner

                if (spinner != null) {
                    val selectedDensidad = spinner.selectedItem as Densidad
                    val franjaHorariaDensidad = FranjaHorariaDensidad(
                        idFranjaHoraria = i,
                        idDensidad = selectedDensidad.id
                    )

                    selectedFranjasDensidades.add(franjaHorariaDensidad)
                }
            }

            (activity as MainActivity).nieblaVM.insertNieblaWithCheck(
                Niebla(
                    fecha = binding.tvFecha.text.toString(),
                    idUsuario = preferences.getInt("idUsuario", -1),
                    hayNiebla = binding.bNiebla.isChecked,
                    franjasDensidades = selectedFranjasDensidades,
                    hayLluvia = binding.bLluvia.isChecked,
                    duracionLluvia = binding.sbDuracionLluvia.progress,
                    hayCorteAgua = binding.bCorteAgua.isChecked,
                    duracionCorteAgua = binding.sbDuracionCorte.progress,
                    incidencia = binding.tvIncidencias.text.toString()
                )
            )

            Toast.makeText(activity, "Niebla insertada", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_datosFragment_to_SecondFragment)
        }
    }


    fun modificar(idNiebla: Int){
        val preferences = (activity as MainActivity)
            .getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        if(preferences.getInt( "idUsuario", -1) != -1) {
            val selectedFranjasDensidades = mutableListOf<FranjaHorariaDensidad>()

            for (i in 0 until binding.lFranjasHorarias.childCount) {
                val rowLayout = binding.lFranjasHorarias.getChildAt(i) as? LinearLayout
                val containerLayout = rowLayout?.getChildAt(1) as? LinearLayout
                val spinner = containerLayout?.getChildAt(0) as? Spinner

                if (spinner != null) {
                    val selectedDensidad = spinner.selectedItem as Densidad
                    val franjaHorariaDensidad = FranjaHorariaDensidad(
                        idFranjaHoraria = i,
                        idDensidad = selectedDensidad.id
                    )

                    selectedFranjasDensidades.add(franjaHorariaDensidad)
                }
            }

            (activity as MainActivity).nieblaVM.actualizar(
                Niebla(
                    idNiebla,
                    fecha = binding.tvFecha.text.toString(),
                    idUsuario = preferences.getInt( "idUsuario", -1),
                    hayNiebla = binding.bNiebla.isChecked,
                    franjasDensidades = selectedFranjasDensidades,
                    hayLluvia = binding.bLluvia.isChecked,
                    duracionLluvia = binding.sbDuracionLluvia.progress,
                    hayCorteAgua = binding.bCorteAgua.isChecked,
                    duracionCorteAgua = binding.sbDuracionCorte.progress,
                    incidencia = binding.tvIncidencias.text.toString()
                )
            )
        }
        Toast.makeText(activity,"Niebla modificada", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_datosFragment_to_SecondFragment)
    }

    fun borrar(miNiebla:Niebla){
        (activity as MainActivity).nieblaVM.borrar(miNiebla)
        Toast.makeText(activity,"Niebla eliminada", Toast.LENGTH_LONG).show()
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

    private fun obtenerFranjaHorariaPorTexto(texto: String): FranjaHoraria? {
        val franjasHorarias = (activity as MainActivity).nieblaVM.listaFranjasHorarias.value

        if (franjasHorarias != null) {
            for (franjaHoraria in franjasHorarias) {
                if (franjaHoraria.franja == texto) {
                    return franjaHoraria
                }
            }
        }

        return null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        if (idNiebla!=-1) (activity as MainActivity).nieblaVM.miNiebla.removeObservers(activity as MainActivity)
        (activity as MainActivity).usuarioVM.miUsuario.removeObservers(activity as MainActivity)
    }
}