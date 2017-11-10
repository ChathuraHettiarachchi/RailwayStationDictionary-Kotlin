package com.chootdev.railwaydic.helpers

import android.content.Context

object UserDefaults {
    fun isDBUpdated(context: Context): Boolean {
        val pref = context.getSharedPreferences(Const.PREF, Context.MODE_PRIVATE)
        return pref.getBoolean(Const.PREF_ISINIT, false)
    }

    fun updateInit(context: Context, status: Boolean) {
        val pref = context.getSharedPreferences(Const.PREF, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean(Const.PREF_ISINIT, status)
        editor.apply()
    }
}
