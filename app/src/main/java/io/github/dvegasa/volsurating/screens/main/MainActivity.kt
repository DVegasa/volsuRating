package io.github.dvegasa.volsurating.screens.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.dvegasa.volsurating.R
import io.github.dvegasa.volsurating.Subject
import io.github.dvegasa.volsurating.screens.welcome.WelcomeActivity
import io.github.dvegasa.volsurating.storage.UserDataStorage
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val userDataStorage = UserDataStorage(this)
    private val adapter = RvSubjectsAdapter(arrayListOf())

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

        setSupportActionBar(toolbarDef)
        supportActionBar?.setTitle("ВолГУ рейтинг")

        prepareUI()
    }

    private fun prepareUI() {
        adapter.list = arrayListOf(
            Subject("\uD83D\uDC4D", "Алгебра и теория чисел", 47),
            Subject("❗", "Математический анализ", 12),
            Subject("✅", "Геометрия и топология", 100),
            Subject("\uD83D\uDC4D", "Информатика и програ...", 63),
            Subject("\uD83D\uDD30", "Русский язык", 65)
        )

        rvSubjects.adapter = adapter
        val ll = LinearLayoutManager(this)
        rvSubjects.layoutManager = ll
        rvSubjects.addItemDecoration(
            DividerItemDecoration(rvSubjects.context, ll.orientation)
        )
    }
}
