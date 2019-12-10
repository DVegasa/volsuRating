package io.github.dvegasa.volsurating.screens.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.github.dvegasa.volsurating.R
import kotlinx.android.synthetic.main.fragment_zachetka_picker.*
import kotlinx.android.synthetic.main.fragment_zachetka_picker.view.*

const val ZACHETKA_PICKER_URL = "https://volsu.ru/rating/"

class ZachetkaPickerFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_zachetka_picker, container, false)

        initializeUI(v)

        return v
    }

    private fun initializeUI(v: View) {
        v.btnConfirm.setOnClickListener {
            confirmPick()
        }

        v.webView.loadUrl(ZACHETKA_PICKER_URL)
        v.webView.settings.javaScriptEnabled = true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun confirmPick() {
        listener?.onZachetkaPicked(webView.url)
    }

    interface OnFragmentInteractionListener {
        fun onZachetkaPicked(url: String)
    }

}
