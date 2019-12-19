package io.github.dvegasa.volsurating.storage

import android.content.Context
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import io.github.dvegasa.volsurating.data_processing.BroadcastEvents
import io.github.dvegasa.volsurating.models.SubjectRich
import io.github.dvegasa.volsurating.models.UserData

/**
 * 11.12.2019
 */

const val SHARPREF_NAME = "app"

const val SHARPREF_USERDATA_ZACHETKA = "zachetkaId"
const val SHARPREF_USERDATA_SEMESTR = "semestr"
const val SHARPREF_USERDATA_GROUPNAME = "groupName"
const val SHARPREF_USERDATA_PLANID = "planId"

const val SHARPREF_SUBJECTRICHES = "subjectRiches"

class SharedPrefCache(val context: Context) {

    private val sharPref by lazy {
        context.getSharedPreferences(SHARPREF_NAME, Context.MODE_PRIVATE)
    }

    private val settingsSharPref by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun isUserDataSaved(): Boolean {
        return sharPref.contains(SHARPREF_USERDATA_PLANID)
                && sharPref.contains(SHARPREF_USERDATA_ZACHETKA)
                && sharPref.contains(SHARPREF_USERDATA_GROUPNAME)
                && sharPref.contains(SHARPREF_USERDATA_SEMESTR)
    }

    fun saveUserData(data: UserData) {
        val editor = sharPref.edit()
        editor.putInt(SHARPREF_USERDATA_ZACHETKA, data.zachetkaId)
        editor.putInt(SHARPREF_USERDATA_SEMESTR, data.semestr)
        editor.putString(SHARPREF_USERDATA_GROUPNAME, data.groupName)
        editor.putString(SHARPREF_USERDATA_PLANID, data.planId)
        editor.apply()
    }

    fun getUserData(): UserData {
        return UserData(
            zachetkaId = sharPref.getInt(SHARPREF_USERDATA_ZACHETKA, 0),
            semestr = settingsSharPref.getString(SHARPREF_USERDATA_SEMESTR, "1")!!.toInt(),
            groupName = sharPref.getString(SHARPREF_USERDATA_GROUPNAME, "") ?: "",
            planId = sharPref.getString(SHARPREF_USERDATA_PLANID, "") ?: ""
        )
    }

    fun clearUserData() {
        val editor = sharPref.edit()
        editor.remove(SHARPREF_USERDATA_ZACHETKA)
        editor.remove(SHARPREF_USERDATA_SEMESTR)
        editor.remove(SHARPREF_USERDATA_GROUPNAME)
        editor.remove(SHARPREF_USERDATA_PLANID)
        editor.remove(SHARPREF_SUBJECTRICHES)
        editor.apply()
    }

    fun isSubjectRichSaved(): Boolean {
        return sharPref.contains(SHARPREF_SUBJECTRICHES)
    }

    fun saveSubjectRiches(list: List<SubjectRich>) {
        val editor = sharPref.edit()
        val jsoned = Gson().toJson(list)
        editor.putString(SHARPREF_SUBJECTRICHES, jsoned)
        editor.apply()
        BroadcastEvents().sendDataUpdated(context, ArrayList(list))
    }

    fun getSubjectRiches(): ArrayList<SubjectRich> {
        val jsoned = sharPref.getString(SHARPREF_SUBJECTRICHES, "0")
        val result = Gson().fromJson(jsoned, Array<SubjectRich>::class.java).toList()
        return result as ArrayList<SubjectRich>
    }

    fun clearSubjectRiches() {
        val editor = sharPref.edit()
        editor.remove(SHARPREF_SUBJECTRICHES)
        editor.apply()
    }
}