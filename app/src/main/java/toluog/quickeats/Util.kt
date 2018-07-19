package toluog.quickeats

import android.content.Context
import org.jetbrains.anko.defaultSharedPreferences

object Util {
    val FIRST_OPEN = "first_open"

    fun firstOpen(context: Context) = context.defaultSharedPreferences.getBoolean(FIRST_OPEN, true)

    fun setBoolPref(context: Context, key: String, value: Boolean) {
        val sharedPref = context.defaultSharedPreferences
        with(sharedPref.edit()) {
            putBoolean(key, value)
            apply()
        }
    }
}