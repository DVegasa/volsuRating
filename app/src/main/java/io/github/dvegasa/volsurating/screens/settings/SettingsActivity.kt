package io.github.dvegasa.volsurating.screens.settings

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import io.github.dvegasa.volsurating.BuildConfig
import io.github.dvegasa.volsurating.R
import io.github.dvegasa.volsurating.data_processing.UsefullDataManager
import io.github.dvegasa.volsurating.screens.main.MainActivity
import io.github.dvegasa.volsurating.storage.SharedPrefCache
import kotlinx.android.synthetic.main.activity_settings.*


class SettingsActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, key: String?) {
        if (key == "semestr") {
            Log.d("ed__", "onSharedPreferenceChanged: key = 'semestr'")
            UsefullDataManager(this).forceRefrestData()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setSupportActionBar(toolbarDef)
        setTheme(R.style.AppTheme_SettingsActivityStyle)
        supportActionBar?.title = "Настройки"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
    }

    override fun onResume() {
        super.onResume()
        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onDestroy() {
        PreferenceManager.getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(this)
        super.onDestroy()
    }


    class SettingsFragment : PreferenceFragmentCompat() {

        private val handler = Handler()

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(
                R.xml.root_preferences,
                rootKey
            )

            findPreference<Preference>("settingsLogout")?.setOnPreferenceClickListener {
                if (context != null) {
                    clearUserData(context!!)
                }
                true
            }

            findPreference<Preference>("settingsAuthor")?.setOnPreferenceClickListener {
                if (context != null) {
                    Toast.makeText(context, "♥", Toast.LENGTH_SHORT).show()
                }
                true
            }

            findPreference<Preference>("settingsContactDeveloper")?.setOnPreferenceClickListener { pref ->
                pref.title = "Ожидайте..."
                pref.isEnabled = false
                Thread(Runnable {
                    Thread.sleep(5000L)
                    handler.post {
                        pref.isEnabled = true
                        pref.title = "Связь с разработчиком"
                    }
                })
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.me/dvegasa")))
                true
            }

            findPreference<Preference>("settingsVersion")?.summary =
                "Версия приложения ${BuildConfig.VERSION_NAME}"
            if (context != null) {
                findPreference<ListPreference>("semestr")?.apply {
                    summary =
                        "Сейчас отображается: ${SharedPrefCache(context!!).getUserData().semestr}"
                    setOnPreferenceChangeListener { preference, newValue ->
                        summary = "Сейчас отображается: ${newValue}"
                        true
                    }
                }
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