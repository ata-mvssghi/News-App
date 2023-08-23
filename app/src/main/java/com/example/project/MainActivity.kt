package com.example.project

//noinspection SuspiciousImport
import com.example.project.R
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.project.fragmnets.FontScaleChangedListener
import com.example.project.fragmnets.OnThemeChangeListener
import com.example.project.fragmnets.SettingsFragment
import com.example.project.fragmnets.onApiSettingChangedListner


class MainActivity : AppCompatActivity(),FontScaleChangedListener,OnThemeChangeListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val themePreference = preferences.getString("color_theme","Light") // Default to light theme
        val fontPreference=preferences.getString("text_size","Medium")//Medium as def
        themeSetting(themePreference)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SettingsFragment.fontScaleChangedListener = this
        SettingsFragment.ThemeChangingInstance.themeChangeListener=this
        if (fontPreference != null) {
            applyFontScaleConfiguration(this ,fontPreference)
        }
    }
    fun applyFontScaleConfiguration(context: Context, selectedTextSizePreference: String) {
        val configuration = context.resources.configuration
        configuration.fontScale = when (selectedTextSizePreference) {
            "Very small"->0.5f
            "Small" -> 0.7f // Adjust as needed
            "Medium" -> 1f // Default font scale
            "Large" -> 1.1f // Adjust as needed
            "Very large"->1.5f
            else -> 1.0f // Default font scale
        }
        Log.i("remote","dddddddddddddddddddddddddddddddd")

        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
    }
    override fun onFontScaleChanged(selectedTextSizePreference: String) {
//        Log.i("remote","passed data main= $selectedTextSizePreference")
//        applyFontScaleConfiguration(this, selectedTextSizePreference)
        recreate()
    }
    fun themeSetting(selectedTheme:String?){
        when (selectedTheme){
            "Light"->{
                setTheme(R.style.LightMode)
            }
            "Dark"->{
                setTheme(R.style.DarkMode)
            }
            "SkyBlue"->{
                setTheme(R.style.SkyBlue)
            }
            "Colorful"->{
                setTheme(R.style.Colorful)
            }
            else->{
                setTheme(R.style.LightMode)
            }
        }
    }

    override fun onThemeChanged(selectedThemeString: String?) {
        recreate()
        Log.i("remote","theme change called in main")
    }

}