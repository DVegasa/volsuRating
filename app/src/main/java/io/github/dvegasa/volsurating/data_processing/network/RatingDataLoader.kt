package io.github.dvegasa.volsurating.data_processing.network

import VolsuRatingHtmlParser
import android.util.Log
import io.github.dvegasa.volsurating.data_processing.BroadcastEvents
import io.github.dvegasa.volsurating.models.SubjectRich
import io.github.dvegasa.volsurating.models.UserData
import io.github.dvegasa.volsurating.storage.SharedPrefCache
import org.jsoup.Jsoup

/**
 * 15.12.2019
 */
class RatingDataLoader(private val cache: SharedPrefCache) {

    fun loadAndCacheData(userData: UserData) {
        if (userData.planId.isEmpty()) {
            throw Exception("UserData is null")
        }
        Thread(Runnable {
            Log.d("ed__", "Loading started...")
            try {
                val url = userData.getUrl()
                val doc = Jsoup.connect(url).get()
                val volsuParser = VolsuRatingHtmlParser(doc)
                val schoolboys = volsuParser.getSchoolboysData()
                val lessons = volsuParser.getLessonsData()

                val subjectRiches = Array(lessons.size) { SubjectRich("none", mutableListOf(), -1) }

                for (i in subjectRiches.indices) {
                    schoolboys.forEach { entry ->
                        subjectRiches[i].rates.add(entry.value[i])
                    }
                    subjectRiches[i].name = lessons[i][0]
                    subjectRiches[i].ekzamen = lessons[i][1]
                    subjectRiches[i].userRate = schoolboys[userData.zachetkaId]?.get(i) ?: -1
                }

                Log.d("ed__", "Loading ended!")
                subjectRiches.forEach {
                    Log.d("ed__", it.toString())
                }

                cache.saveSubjectRiches(subjectRiches.toList())
            } catch (e: Exception) {
                BroadcastEvents().sendErrorCriticalParsing(cache.context, e)
                return@Runnable
            }
        }).start()
    }
}