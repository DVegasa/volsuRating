package io.github.dvegasa.volsurating.screens.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.dvegasa.volsurating.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ZachetkaPickerFragment.OnFragmentInteractionListener {

    private val zachetkaPickerFragment = ZachetkaPickerFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnNext.setOnClickListener {
            openZachetkaPicker()
        }
    }

    private fun openZachetkaPicker() {
        supportFragmentManager.beginTransaction().apply {
            add(android.R.id.content, zachetkaPickerFragment)
            addToBackStack(null)
            commit()
        }
    }

    override fun onZachetkaPicked(url: String) {
        supportFragmentManager.beginTransaction().apply {
            remove(zachetkaPickerFragment)
            commit()
        }
    }
}
