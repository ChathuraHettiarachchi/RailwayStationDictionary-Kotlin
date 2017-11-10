package com.chootdev.railwaydic.database.access

import android.content.Context
import com.chootdev.railwaydic.database.Crud
import com.chootdev.railwaydic.database.manager.DatabaseHelper
import com.chootdev.railwaydic.database.manager.DatabaseManager
import com.chootdev.railwaydic.models.Station
import java.lang.Exception

/**
 * Created by Choota on 11/10/17.
 */
class StationAccess(context: Context) : Crud {

    var helper: DatabaseHelper? = null

    init {
        DatabaseManager.init(context)
        helper = DatabaseManager.instance?.helper
    }

    override fun create(item: Any): Int {
        var index: Int = -1

        val data = item as Station
        index = helper?.getStationDao()!!.create(data)

        return index
    }

    override fun update(item: Any): Int {
        var index: Int = -1

        val data = item as Station
        index = helper?.getStationDao()!!.update(data)

        return index
    }

    override fun delete(item: Any): Int {
        var index: Int = -1

        val data = item as Station
        index = helper?.getStationDao()!!.delete(data)

        return index
    }

    override fun findById(id: Int): Any {
        return helper?.getStationDao()!!.queryForId(id)
    }

    override fun deleteAll(): Boolean {
        return try{
            helper?.deleteStations()
            true
        } catch (e: Exception){
            e.printStackTrace()
            false
        }
    }

    override fun findAll(): List<*> {
        return helper?.getStationDao()!!.queryForAll()
    }
}