package com.rema.pollutioncontrol.repository.prefs

import android.content.Context
import android.content.res.Configuration
import android.view.ContextThemeWrapper
import android.widget.Toast
import java.util.*

class LanguagePrefs(var context: Context) : Prefs(context) {
    override var key: String = "APP_LANGUAGE"
    fun setLanguage(completionHandler: () -> Unit) {
        changeLanguage(Locale(getLanguage()), completionHandler)
    }

    fun getLanguage(): String {
        return if (get().isNotEmpty()) get() else "en"
    }

    private fun changeLanguage(locale: Locale, completionHandler: () -> Unit) {

        if (context.resources.configuration.locale == locale) {
            return
        }
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.setLocale(locale)
        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
        completionHandler()
    }


}