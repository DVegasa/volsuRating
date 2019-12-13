package io.github.dvegasa.volsurating.data_processing

import android.util.Log
import io.github.dvegasa.volsurating.models.UserData
import java.net.URLDecoder


/**
 * 10.12.2019
 */

val matchingRegex = """
            https:\/\/volsu.ru\/rating\/\?plan_id=(\S+)&zach=(\d+)&semestr=(\d)&group=(\S+)
        """.trimIndent()

class UrlParser(var url: String) {

    init {
        url = decode(url)
    }

    fun getUserData(): UserData {
        val regex = Regex(matchingRegex)
        val matchings = regex.find(url)
        try {
            val list = matchings!!.groupValues
            val planId = list[1]
            val zachetkaId = list[2]
            val semestr = list[3]
            val groupName = list[4]

            Log.d("ed__", "Parser 1: \n$zachetkaId\n$semestr\n$groupName")

            val data = UserData(
                zachetkaId.toInt(),
                semestr.toInt(),
                groupName,
                planId
            )
            Log.d("ed__", "Parser 2: $data")
            return data
        } catch (ex: Exception) {
            throw ex
        }
    }

    fun decode(url: String): String {
        return URLDecoder.decode(url, "UTF-8")
    }
}