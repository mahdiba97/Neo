package com.mahdiba97.notes

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import java.util.*


class PrefHelper {
    companion object {
        fun pref(context: Context): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)


        fun setTheme(context: Context) {
            when (pref(context).getString(THEME_KEY, "Default").toString()) {
                "Light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                "Night" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }

        fun setLanguage(context: Context) {

            val local = when (pref(context).getString(LANGUAGE_KEY, "Default").toString()) {
                "Persian" -> Locale("fa")
                else -> Locale("en")
            }
            Log.i("Pref", local.displayLanguage)
            val resources = context.resources
            Locale.setDefault(local)
            resources.configuration.setLocale(local)
            resources.updateConfiguration(resources.configuration, resources.displayMetrics)
        }
    }
}