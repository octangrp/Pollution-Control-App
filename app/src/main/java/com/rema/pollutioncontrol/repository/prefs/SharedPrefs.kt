package com.rema.pollutioncontrol.repository.prefs

import android.content.Context
import android.content.SharedPreferences

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
abstract class SharedPrefs(context: Context) {
    protected abstract var key: String
    private var prefs: SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = prefs.edit()


    protected fun get(default: String = ""): String {
        return prefs.getString(key, default)
    }

    protected fun getInt(default: Int = 0): Int {
        return prefs.getInt(key, default)
    }


    protected fun save(value: String) {
        editor.putString(key, value).commit()
    }
}