package com.mahdiba97.notes

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import java.util.*


class PrefHelper {
  companion object {
    private fun pref(context: Context): SharedPreferences =
      PreferenceManager.getDefaultSharedPreferences(context)


    fun setTheme(context: Context) {
      when (pref(context).getString(THEME_KEY, "Default").toString()) {
        "Light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        "Night" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
      }
      context.setTheme(R.style.Theme_Neo)
    }

    fun setLanguage(context: Context) {
      val local = when (pref(context).getString(LANGUAGE_KEY, "Default").toString()) {
        "Persian" -> Locale("fa")
        else -> Locale("en")
      }
      val resources = context.resources
      Locale.setDefault(local)
      resources.configuration.setLocale(local)
      resources.updateConfiguration(resources.configuration, resources.displayMetrics)
    }

    fun getLanguage(context: Context): String {
      return pref(context).getString(LANGUAGE_KEY, "English").toString()
    }

    fun getTheme(context: Context): String {
      return pref(context).getString(THEME_KEY, "Default").toString()
    }
  }
}
