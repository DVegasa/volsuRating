package io.github.dvegasa.volsurating.storage

import android.content.Context
import io.github.dvegasa.volsurating.models.UserData

/**
 * 11.12.2019
 */

const val SHARPREF_NAME = "app"

const val SHARPREF_PARAM_ZACHETKA = "zachetkaId"
const val SHARPREF_PARAM_SEMESTR = "semestr"
const val SHARPREF_PARAM_GROUPNAME = "groupName"
const val SHARPREF_PARAM_PLANID = "planId"

class SharedPrefCache(val context: Context) {

    private val sharPref by lazy {
        context.getSharedPreferences(SHARPREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveUserData(data: UserData) {
        val editor = sharPref.edit()
        editor.putInt(SHARPREF_PARAM_ZACHETKA, data.zachetkaId)
        editor.putInt(SHARPREF_PARAM_SEMESTR, data.semestr)
        editor.putString(SHARPREF_PARAM_GROUPNAME, data.groupName)
        editor.putString(SHARPREF_PARAM_PLANID, data.planId)
        editor.apply()
    }

    fun isUserDataSaved(): Boolean {
        return sharPref.contains(SHARPREF_PARAM_PLANID)
                && sharPref.contains(SHARPREF_PARAM_ZACHETKA)
                && sharPref.contains(SHARPREF_PARAM_GROUPNAME)
                && sharPref.contains(SHARPREF_PARAM_SEMESTR)
    }

    fun getUserData(): UserData {
        return UserData(
            zachetkaId = sharPref.getInt(SHARPREF_PARAM_ZACHETKA, 0),
            semestr = sharPref.getInt(SHARPREF_PARAM_SEMESTR, 0),
            groupName = sharPref.getString(SHARPREF_PARAM_GROUPNAME, "") ?: "",
            planId = sharPref.getString(SHARPREF_PARAM_PLANID, "") ?: ""
        )
    }
}