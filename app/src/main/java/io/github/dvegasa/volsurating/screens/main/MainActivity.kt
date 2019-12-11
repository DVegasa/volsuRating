package io.github.dvegasa.volsurating.screens.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.github.dvegasa.volsurating.R
import io.github.dvegasa.volsurating.storage.UserData
import io.github.dvegasa.volsurating.storage.UserDataStorage
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ZachetkaPickerFragment.OnFragmentInteractionListener {

    private val zachetkaPickerFragment = ZachetkaPickerFragment()
    private val userDataStorage = UserDataStorage(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbarDef)
        supportActionBar?.setTitle("ВолГУ рейтинг")

        if (userDataStorage.isUserDataSaved()) {
            Log.d("ed__", "Data saved:\n${userDataStorage.getUserData()}")
        }

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

    override fun onZachetkaPicked(data: UserData) {
        supportFragmentManager.beginTransaction().apply {
            remove(zachetkaPickerFragment)
            commit()
        }
        Log.d("ed__", "Data received: $data")
        userDataStorage.saveUserData(data)
    }
}
