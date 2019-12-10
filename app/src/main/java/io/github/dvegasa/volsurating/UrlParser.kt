package io.github.dvegasa.volsurating

import android.util.Log
import java.net.URLDecoder


/**
 * 10.12.2019
 */

val matchingRegex = """
            https:\/\/volsu.ru\/rating\/\?plan_id=\S+&zach=(\d+)&semestr=(\d)&group=(\S+)
        """.trimIndent()

class UrlParser(var url: String) {

    init {
        url = decode(url)
    }

    fun getData(): Data {
        val regex = Regex(matchingRegex)
        val matchings = regex.find(url)
        try {
            val list = matchings!!.groupValues
            val zachetkaId = list[1]
            val semestr = list[2]
            val groupName = list[3]

            Log.d("ed__", "\n$zachetkaId\n$semestr\n$groupName")

            val data = Data(
                zachetkaId.toInt(),
                semestr.toInt(),
                groupName
            )
            return data
        } catch (ex: Exception) {
            throw ex
        }
    }

    fun decode(url: String): String {
        return URLDecoder.decode(url, "UTF-8")
    }

    data class Data(
        val zachetkaId: Int,
        val semestr: Int,
        val groupName: String
    )
}