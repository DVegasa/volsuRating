package io.github.dvegasa.volsurating

import android.os.Handler
import android.webkit.WebView

/**
 * 10.12.2019
 */
class UrlAutoDetector(val webView: WebView, val cb: Callback) {

    val handler = Handler()

    val thread = Thread(Runnable {
        try {
            while (!Thread.interrupted()) {
                var url: String

                handler.post {
                    url = webView.url
                    if (isUrlValid(url)) {
                        cb.urlMatchesPattern(url)
                    } else {
                        cb.urlDoesntMatchPattern(url)
                    }
                }

                Thread.sleep(1000L)
            }
        } catch (ex: InterruptedException) {
            Thread.currentThread().interrupt()
        }
    })

    fun start() {
        thread.start()
    }

    fun stop() {
        thread.interrupt()
    }

    private fun isUrlValid(url: String): Boolean {
        /* Valid URLs:
         https://volsu.ru/rating/?plan_id=%D0%90%D0%A0%D0%9C003689&zach=962941&semestr=1&group=%D0%9C%D0%9E%D0%A1%D0%B1-192
         https://volsu.ru/rating/?plan_id=АРМ002833&zach=962941&semestr=1&group=МОСб-182
         */
        val regex = """
            https:\/\/volsu.ru\/rating\/\?plan_id=\S+&zach=\d+&semestr=\d&group=\S+
        """.trimIndent()

        return url.matches(Regex(regex))
    }

    interface Callback {
        fun urlMatchesPattern(url: String)
        fun urlDoesntMatchPattern(url: String)
    }
}