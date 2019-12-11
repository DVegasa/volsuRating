package io.github.dvegasa.volsurating.screens.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.github.dvegasa.volsurating.R
import io.github.dvegasa.volsurating.screens.welcome.WelcomeActivity
import io.github.dvegasa.volsurating.storage.UserDataStorage

class MainActivity : AppCompatActivity() {

    private val userDataStorage = UserDataStorage(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (userDataStorage.isUserDataSaved()) {
            Log.d("ed__", "Data saved:\n${userDataStorage.getUserData()}")
        } else {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }
    }
}
