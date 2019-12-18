package io.github.dvegasa.volsurating.data_processing

import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.Gson
import io.github.dvegasa.volsurating.models.SubjectRich

/**
 * 15.12.2019
 */
class BroadcastEvents {
    companion object {
        const val EVENT_FILTER = "usefullDataLocalBroadcast"

        const val DATA_UPDATED = "dataUpdated"
        const val ERROR_CRITICAL_PARSING = "errorCriticalParsing"
    }

    fun sendDataUpdated(context: Context, data: ArrayList<SubjectRich>) {
        val json = Gson().toJson(data)
        val intent = Intent(EVENT_FILTER)
        intent.putExtra("status", DATA_UPDATED)
        intent.putExtra("msg", json)
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }

    fun sendErrorCriticalParsing(context: Context, e: Exception?) {
        e?.printStackTrace()
        val intent = Intent(EVENT_FILTER)
        intent.putExtra("status", ERROR_CRITICAL_PARSING)
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }
}