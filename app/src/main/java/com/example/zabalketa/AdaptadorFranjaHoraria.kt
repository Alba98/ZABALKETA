import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.zabalketa.FranjaHoraria

class AdaptadorFranjaHoraria(context: Context, val franjas: List<FranjaHoraria>) : ArrayAdapter<FranjaHoraria>(context, android.R.layout.simple_spinner_item, franjas) {
    override fun getItemId(position: Int): Long {
        return franjas[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = franjas[position].franja
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = franjas[position].franja
        return view
    }
}
