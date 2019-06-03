package com.rema.pollutioncontrol.repository.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
abstract class Prefs(context: Context) {
    protected abstract var key: String
    private var prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private var editor: SharedPreferences.Editor = prefs.edit()


    protected fun get(default: String = ""): String {
        return prefs.getString(key, default)
    }

    protected fun save(value: String) {
        editor.putString(key, value).commit()
    }
}