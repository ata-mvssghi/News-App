package com.example.project.fragmnets
import DatePickerPreference
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import androidx.preference.DialogPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.project.R
import java.text.DateFormat
import java.util.Calendar

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)


        val datePreference = findPreference<DatePickerPreference>("date_preference")
        datePreference?.setOnPreferenceClickListener {
            showDatePickerDialog(datePreference)
            true
        }
    }
    private fun showDatePickerDialog(preference: DatePickerPreference) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                preference.setDate(calendar.timeInMillis)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
}
