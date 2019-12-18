package io.github.dvegasa.volsurating.screens.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.github.dvegasa.volsurating.Emoji
import io.github.dvegasa.volsurating.R
import io.github.dvegasa.volsurating.data_processing.BroadcastEvents
import io.github.dvegasa.volsurating.data_processing.Statistics
import io.github.dvegasa.volsurating.data_processing.UsefullDataManager
import io.github.dvegasa.volsurating.models.SubjectRich
import io.github.dvegasa.volsurating.screens.settings.SettingsActivity
import io.github.dvegasa.volsurating.screens.welcome.WelcomeActivity
import io.github.dvegasa.volsurating.storage.SharedPrefCache
import kotlinx.android.synthetic.main.activity_main.*

inline fun <reified T> Gson.fromJson(json: String) =
    this.fromJson<T>(json, object : TypeToken<T>() {}.type)

class MainActivity : AppCompatActivity() {

    private val cache = SharedPrefCache(this)
    private val adapter = RvSubjectsAdapter(arrayListOf())
    private val usefullDataManager = UsefullDataManager(this)

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            Log.d("ed__", "MainActivity: Data updated!")
            swipeRvSubjects.isRefreshing = false
            val status = p1?.getStringExtra("status")

            if (status == BroadcastEvents.DATA_UPDATED) {
                val dataJsoned = p1?.getStringExtra("msg") ?: ""
                val data: ArrayList<SubjectRich> = Gson().fromJson(dataJsoned)

                showData(data)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (cache.isUserDataSaved()) {
            Log.d("ed__", "Data saved:\n${cache.getUserData()}")
        } else {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
            return
        }
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(broadcastReceiver, IntentFilter(BroadcastEvents.EVENT_FILTER))

        prepareUI()
        try {
            usefullDataManager.requestData()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Ошибка загрузки данных", Toast.LENGTH_LONG).show()
            cache.clearUserData()
            finish()
        }
    }



    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionSettings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun prepareUI() {
        setSupportActionBar(toolbarDef)
        supportActionBar?.setTitle("ВолГУ рейтинг")

        adapter.updateList(
            arrayListOf(
                SubjectRich("Идёт загрузка...", mutableListOf(), -1)
            )
        )

        rvSubjects.adapter = adapter
        val ll = LinearLayoutManager(this)
        rvSubjects.layoutManager = ll
        rvSubjects.addItemDecoration(
            DividerItemDecoration(rvSubjects.context, ll.orientation)
        )

        swipeRvSubjects.setOnRefreshListener {
            usefullDataManager.forceRefrestData()
        }
    }

    private fun showData(list: ArrayList<SubjectRich>) {
        adapter.updateList(list)
        tvScore.setText(Statistics.userSum(list).toString())
        val userRating = Statistics.userRating(list)
        if (userRating == 1) {
            tvRating.setText(Emoji.crown)
        } else {
            tvRating.setText(userRating.toString())
        }
    }
}
