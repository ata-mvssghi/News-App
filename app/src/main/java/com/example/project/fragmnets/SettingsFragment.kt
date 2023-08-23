package com.example.project.fragmnets
import DatePickerPreference
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.util.Xml
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.project.R
import com.example.project.fragmnets.SettingsFragment.ApiChangeInstance.apiChangeListener
import com.example.project.fragmnets.SettingsFragment.ThemeChangingInstance.themeChangeListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class SettingsFragment : PreferenceFragmentCompat(),Preference.OnPreferenceChangeListener {
    var previousTheme:String?="init"
    var previousFont:String?="init"
    companion object {
        var fontScaleChangedListener: FontScaleChangedListener? = null
    }
    object ThemeChangingInstance{
         var themeChangeListener: OnThemeChangeListener? = null
    }
    object ApiChangeInstance{
        var apiChangeListener:onApiSettingChangedListner?=null
    }
    override fun onDestroy() {
        super.onDestroy()
        val sp =PreferenceManager.getDefaultSharedPreferences(requireContext())
        val selectedTextSizePreference = sp.getString("text_size", "Medium")
        val selectedTheme:String?=sp.getString("color_theme","Light")
        val order=sp.getString("order_by","newest")
        val time:Long=sp.getLong("date_preference",1)
         // Check if the time is not the default value
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formattedDate = dateFormat.format(Date(time))
            Log.i("remote", "time is =$formattedDate")
            // Now, `formattedDate` contains the date in the desired format
            // You can use it as needed

        
        if (selectedTextSizePreference != null&&selectedTextSizePreference!=previousFont) {
            onTextSizePreferenceChanged(selectedTextSizePreference)
        }
        if(selectedTheme!=null&&selectedTheme!=previousTheme){
            themeChangeListener?.onThemeChanged(selectedTheme)
            Log.i("remote","theme is cahnged and changed theme is =$selectedTheme")
        }
        apiChangeListener?.onUpdate(formattedDate,order)

    }
     fun onTextSizePreferenceChanged(selectedTextSizePreference: String) {
        // Save selectedTextSizePreference to SharedPreferences
         val sp = PreferenceManager.getDefaultSharedPreferences(requireContext())
        sp.edit().putString("text_size", selectedTextSizePreference).apply()

        Log.i("remote","passed data = $selectedTextSizePreference")
        // Notify MainActivity to update font scale
        fontScaleChangedListener?.onFontScaleChanged(selectedTextSizePreference)
    }


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val sp = PreferenceManager.getDefaultSharedPreferences(requireContext())
         previousTheme=sp.getString("color_theme","Light")
         previousFont=sp.getString("text_size","Medium")

        val datePickerPreference = DatePickerPreference(requireContext())
        datePickerPreference.key = "date_preference"
        datePickerPreference.title = "Select Date"
        datePickerPreference.summary = "Selected Date"

        //setting the last week as the default date
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.WEEK_OF_YEAR, -1) // Subtract one week
        val lastWeekDate = calendar.time
        datePickerPreference.setDefaultValue(lastWeekDate.time)
        datePickerPreference.onPreferenceChangeListener = this

        preferenceScreen.addPreference(datePickerPreference)
    }
    override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {
        Log.i("remote","onPreferenceChange")
        if (preference.key == "date_preference") {
            val selectedDateInMillis = newValue as? Long
            if (selectedDateInMillis != null) {
                val selectedDate = Date(selectedDateInMillis)
                Log.i("remote","DATE=$selectedDate")
            }
        }
        return true
    }
}
interface FontScaleChangedListener {
    fun onFontScaleChanged(selectedTextSizePreference: String)
}
interface OnThemeChangeListener {
    fun onThemeChanged(selectedThemeString:String?)
}
interface onApiSettingChangedListner {
    fun onUpdate(newDate:String,newOrder:String?)
}
