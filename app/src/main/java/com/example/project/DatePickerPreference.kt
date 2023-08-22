import android.app.DatePickerDialog
import android.content.Context
import android.content.res.TypedArray
import android.view.View
import android.widget.DatePicker
import androidx.preference.DialogPreference
import com.example.project.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DatePickerPreference(context: Context) : DialogPreference(context) {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    private var year: Int
    private var month: Int
    private var day: Int
    private var selectedDate: Date? = null

    init {
        // Initialize date
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)

        positiveButtonText = "Set"
        negativeButtonText = "Cancel"
    }

    override fun onClick() {
        val dialog = DatePickerDialog(context, { _, year, month, dayOfMonth ->
            val newDate = Calendar.getInstance()
            newDate.set(year, month, dayOfMonth)
            selectedDate = newDate.time
            persistLong(selectedDate?.time ?: 0)
        }, year, month, day)
        dialog.show()
    }

    override fun onSetInitialValue(defaultValue: Any?) {
        if (defaultValue is Long) {
            selectedDate = Date(defaultValue)
        }
    }

    override fun onGetDefaultValue(a: TypedArray, index: Int): Any? {
        return 0
    }

    override fun getSummary(): CharSequence? {
        return selectedDate?.let { dateFormat.format(it) } ?: super.getSummary()
    }
}
