package io.github.dvegasa.volsurating.screens.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import io.github.dvegasa.volsurating.screens.main.MainActivity
import io.github.dvegasa.volsurating.storage.SharedPrefCache



class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(io.github.dvegasa.volsurating.R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(io.github.dvegasa.volsurating.R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(io.github.dvegasa.volsurating.R.xml.root_preferences, rootKey)
            findPreference<Preference>("settingsLogout")?.setOnPreferenceClickListener {
                if (context != null) {
                    clearUserData(context!!)
                }
                true
            }
        }

        private fun clearUserData(context: Context) {
            val cache = SharedPrefCache(context)
            cache.clearUserData()
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            activity?.finish()
        }
    }
}