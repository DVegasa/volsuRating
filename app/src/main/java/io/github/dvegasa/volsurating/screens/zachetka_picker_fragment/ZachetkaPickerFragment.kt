package io.github.dvegasa.volsurating.screens.zachetka_picker_fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.github.dvegasa.volsurating.data_processing.UrlParser
import io.github.dvegasa.volsurating.models.UserData
import kotlinx.android.synthetic.main.fragment_zachetka_picker.*
import kotlinx.android.synthetic.main.fragment_zachetka_picker.view.*


private val ZACHETKA_PICKER_URL = "https://volsu.ru/rating/"

class ZachetkaPickerFragment : Fragment(), UrlAutoDetector.Callback {

    private var listener: OnFragmentInteractionListener? = null
    private var urlDetector: UrlAutoDetector? = null
    private var curData: UserData? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(
            io.github.dvegasa.volsurating.R.layout.fragment_zachetka_picker,
            container,
            false
        )

        initializeUI(v)
        urlDetector =
            UrlAutoDetector(
                v.webView,
                this
            )
        return v
    }

    private fun initializeUI(v: View) {
        v.btnConfirm.setOnClickListener {
            confirmPick()
        }

        v.webView.loadUrl(ZACHETKA_PICKER_URL)
        v.webView.settings.javaScriptEnabled = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            v.flBottomInformer.elevation = 25f
        }
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

    override fun onResume() {
        super.onResume()
        urlDetector?.start()
    }

    override fun onPause() {
        urlDetector?.stop()
        super.onPause()
    }

    override fun urlDoesntMatchPattern(url: String) {
        setBottomInformerVisibility(false)
    }

    override fun urlMatchesPattern(url: String) {
        setBottomInformerVisibility(true, url)
        Log.d("ed__", url)
    }

    @SuppressLint("SetTextI18n")
    private fun setBottomInformerVisibility(b: Boolean, url: String = "empty") {
        if (b) {
            val parser = UrlParser(url)
            val data = parser.getUserData()
            val text = """
                Зачётка №${data.zachetkaId}
                Группа ${data.groupName}
            """.trimIndent()

            val spannable = SpannableStringBuilder(text)
            spannable.setSpan(
                android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
                9,
                10 + data.zachetkaId.toString().length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannable.setSpan(
                android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
                "Зачётка №${data.zachetkaId}\nГруппа ".length,
                text.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            tvInformerText.setText(spannable)
            curData = data
        }
        flBottomInformer.visibility =
            if (b) View.VISIBLE
            else View.GONE
    }

    private fun confirmPick() {
        urlDetector?.stop()
        listener?.onZachetkaPicked(curData!!)
    }

    interface OnFragmentInteractionListener {
        fun onZachetkaPicked(data: UserData)
    }

}
