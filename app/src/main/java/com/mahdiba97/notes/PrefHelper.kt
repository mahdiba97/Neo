package com.mahdiba97.notes

import android.content.Context
import androidx.preference.PreferenceManager

const val THEME_KEY = "theme_key"
const val LANGUAGE_KEY = "language_key"

class PrefHelper {
    companion object {
        private fun pref(context: Context) = PreferenceManager.getDefaultSharedPreferences(context)


        fun getTheme(context: Context): String {
            return pref(context).getString(THEME_KEY, "Default").toString()
        }

        fun getLanguage(context: Context): String {
            return pref(context).getString(LANGUAGE_KEY, "English").toString()
        }
    }
}