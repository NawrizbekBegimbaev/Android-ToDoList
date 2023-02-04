package packagename.todolist

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import packagename.todolist.databinding.DialogAddBinding
import java.text.SimpleDateFormat
import java.util.*

class AddPurposeDialog:DialogFragment(R.layout.dialog_add){
    private lateinit var binding: DialogAddBinding
    private lateinit var btnstart: Button
    private lateinit var date: TextView
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogAddBinding.bind(view)
        date = binding.tilDate
        btnstart = binding.btnStart
        val mycalendar = Calendar.getInstance()
        val datepick = DatePickerDialog.OnDateSetListener { view, year, month, day ->
            mycalendar.set(Calendar.YEAR, year)
            mycalendar.set(Calendar.MONTH, month)
            mycalendar.set(Calendar.DAY_OF_MONTH, day)
            updatelable(mycalendar)
        }
        val format = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(format, Locale.UK)
        var datee = sdf.format(mycalendar.time)
        binding.apply {
            tilDate.text = datee
            btnStart.setOnClickListener{
                val purpose = etPurpose.text.toString()
                val date = tilDate.text.toString()
                if(purpose.isNotEmpty()){
                    val purposes = Purposes(
                        purpose = purpose,
                        time = date,
                        did = 0
                    )
                    onAddSuccess.invoke(purposes)
                    dismiss()
                } else {
                    Toast.makeText(requireContext(),"Пополните поля", Toast.LENGTH_SHORT).show()
                }

            }
            tilDate.setOnClickListener {
                DatePickerDialog(requireContext(),datepick,mycalendar.get(Calendar.YEAR),mycalendar.get(Calendar.MONTH),mycalendar.get(Calendar.DAY_OF_MONTH)).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun updatelable(myCalendar: Calendar) {
        val format = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(format, Locale.UK)
        date.setText(sdf.format(myCalendar.time))
    }


    private var onAddSuccess: (purpose: Purposes) -> Unit = {}
    fun setOnSuccessListener(onAddSuccess: (purpose: Purposes) -> Unit){
        this.onAddSuccess = onAddSuccess
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.95).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.37).toInt()
        dialog!!.window?.setLayout(width,height)
    }
}