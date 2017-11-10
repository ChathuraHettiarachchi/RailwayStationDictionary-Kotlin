package com.chootdev.railwaydic.database.manager

import android.content.Context

/**
 * Created by Choota on 11/10/17.
 */
class DatabaseManager private constructor(ctx: Context) {

    val helper: DatabaseHelper

    init {
        helper = DatabaseHelper(ctx)
    }

    companion object {
        var instance: DatabaseManager? = null
            private set

        fun init(ctx: Context) {
            if (null == instance) {
                instance = DatabaseManager(ctx)
            }
        }
    }
}
