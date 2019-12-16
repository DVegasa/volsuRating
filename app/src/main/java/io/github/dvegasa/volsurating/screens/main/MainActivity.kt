package io.github.dvegasa.volsurating.screens.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.github.dvegasa.volsurating.R
import io.github.dvegasa.volsurating.data_processing.BroadcastEvents
import io.github.dvegasa.volsurating.data_processing.UsefullDataManager
import io.github.dvegasa.volsurating.models.SubjectRich
import io.github.dvegasa.volsurating.screens.welcome.WelcomeActivity
import io.github.dvegasa.volsurating.storage.SharedPrefCache
import kotlinx.android.synthetic.main.activity_main.*

inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)

class MainActivity : AppCompatActivity() {

    private val userDataStorage = SharedPrefCache(this)
    private val adapter = RvSubjectsAdapter(arrayListOf())
    private val usefullDataManager = UsefullDataManager(this)

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            Log.d("ed__", "MainActivity: Data updated!")
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
        if (userDataStorage.isUserDataSaved()) {
            Log.d("ed__", "Data saved:\n${userDataStorage.getUserData()}")
        } else {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
            return
        }
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(broadcastReceiver, IntentFilter(BroadcastEvents.EVENT_FILTER))

        prepareUI()
        usefullDataManager.requestData()
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }

    private fun prepareUI() {
        setSupportActionBar(toolbarDef)
        supportActionBar?.setTitle("ВолГУ рейтинг")

        adapter.list = arrayListOf(
            SubjectRich("Идёт загрузка...", mutableListOf(), -1)
        )

        rvSubjects.adapter = adapter
        val ll = LinearLayoutManager(this)
        rvSubjects.layoutManager = ll
        rvSubjects.addItemDecoration(
            DividerItemDecoration(rvSubjects.context, ll.orientation)
        )
    }

    private fun showData(list: ArrayList<SubjectRich>) {
        adapter.list = list
        adapter.notifyDataSetChanged()
    }
}
