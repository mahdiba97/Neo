package com.mahdiba97.notes

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        title = getString(R.string.settings)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onStart() {
        super.onStart()
        PrefHelper.pref(this).registerOnSharedPreferenceChangeListener { _, key ->
            if (key == THEME_KEY) PrefHelper.setTheme(this)
            val myIntent = Intent(this, MainActivity::class.java)
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(myIntent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }

}