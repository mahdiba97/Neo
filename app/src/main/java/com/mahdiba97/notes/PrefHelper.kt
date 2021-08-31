package com.mahdiba97.notes

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

const val THEME_KEY = "theme_key"
const val LANGUAGE_KEY = "language_key"

class PrefHelper {
    companion object {
         fun pref(context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)


        fun setTheme(context: Context) {
            when (pref(context).getString(THEME_KEY, "Default").toString()) {
                "Light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                "Night" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }

        fun setLanguage(context: Context) {
        // TODO: Implement adding new language to the application
        }
    }
}