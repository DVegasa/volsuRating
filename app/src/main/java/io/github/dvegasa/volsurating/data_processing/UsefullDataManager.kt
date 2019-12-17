package io.github.dvegasa.volsurating.data_processing

import android.content.Context
import android.util.Log
import io.github.dvegasa.volsurating.data_processing.network.RatingDataLoader
import io.github.dvegasa.volsurating.storage.SharedPrefCache

/**
 * 13.12.2019
 */
class UsefullDataManager(private val context: Context) {

    private val cache = SharedPrefCache(context)
    private val ratingDataLoader = RatingDataLoader(cache)

    fun requestData() {
        if (cache.isSubjectRichSaved()) {
            Log.d("ed__", "SubjectRich is saved locally. Load from cache")
            val data = cache.getSubjectRiches()
            BroadcastEvents().sendDataUpdated(context, data)
        } else {
            ratingDataLoader.loadAndCacheData(cache.getUserData())
        }
    }
    
    fun forceRefrestData() {
        ratingDataLoader.loadAndCacheData(cache.getUserData())
    }
}