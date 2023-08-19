import android.content.Context
import android.util.AttributeSet
import androidx.preference.DialogPreference
import com.example.project.R
import java.text.DateFormat
import java.util.*

class DatePickerPreference(context: Context, attrs: AttributeSet) :
    DialogPreference(context, attrs) {

    private var selectedDate: Long = 0

    init {
        dialogLayoutResource = R.layout.preference_dialog_date_picker
        summaryProvider = SummaryProvider<DatePickerPreference> { preference ->
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = preference.selectedDate
            DateFormat.getDateInstance().format(calendar.time)
        }
    }

    fun setDate(date: Long) {
        selectedDate = date
        persistLong(date)
        notifyChanged()
    }

    fun getDate(): Long {
        return selectedDate
    }
}
