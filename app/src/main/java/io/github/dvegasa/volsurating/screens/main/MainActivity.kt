package io.github.dvegasa.volsurating.screens.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.dvegasa.volsurating.R
import io.github.dvegasa.volsurating.models.SubjectRich
import io.github.dvegasa.volsurating.screens.welcome.WelcomeActivity
import io.github.dvegasa.volsurating.storage.SharedPrefCache
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val userDataStorage = SharedPrefCache(this)
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
            SubjectRich("Алгебра и теория чисел", listOf(10, 20, 30, 30, 30, 30, 20), 42),
            SubjectRich("Алгебра и теория чисел", listOf(55, 20, 30, 33, 30, 30, 20), 42),
            SubjectRich("Алгебра и теория чисел", listOf(11, 20, 30, 30, 30, 30, 20), 42),
            SubjectRich("Алгебра и теория чисел", listOf(43, 20, 30, 30, 30, 30, 20), 42),
            SubjectRich("Алгебра и теория чисел", listOf(10, 63, 30, 30, 30, 30, 20), 42)
        )

        rvSubjects.adapter = adapter
        val ll = LinearLayoutManager(this)
        rvSubjects.layoutManager = ll
        rvSubjects.addItemDecoration(
            DividerItemDecoration(rvSubjects.context, ll.orientation)
        )
    }
}
