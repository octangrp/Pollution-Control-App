package com.rema.pollutioncontrol.repository

import android.content.Context
import android.content.SharedPreferences

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
open class Prefs(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = prefs.edit()


    protected fun get(key: String, default: String = ""): String {
        return prefs.getString(key, default)
    }


    protected fun save(parameterName: String, value: String) {
        editor.putString(parameterName, value).commit()
    }
}