package io.github.dvegasa.volsurating.screens.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.github.dvegasa.volsurating.R
import io.github.dvegasa.volsurating.UrlParser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ZachetkaPickerFragment.OnFragmentInteractionListener {

    private val zachetkaPickerFragment = ZachetkaPickerFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbarDef)
        supportActionBar?.setTitle("ВолГУ рейтинг")

        btnNext.setOnClickListener {
            openZachetkaPicker()
            btnNext.isEnabled = false
            Thread(Runnable {
                Thread.sleep(1000L)
                btnNext.post {
                    btnNext.isEnabled = true
                }
            }).start()
        }
    }

    private fun openZachetkaPicker() {
        supportFragmentManager.beginTransaction().apply {
            add(android.R.id.content, zachetkaPickerFragment)
            addToBackStack(null)
            commit()
        }
    }

    override fun onZachetkaPicked(data: UrlParser.Data) {
        supportFragmentManager.beginTransaction().apply {
            remove(zachetkaPickerFragment)
            commit()
        }
        Log.d("ed__", "Data received: $data")
    }
}
