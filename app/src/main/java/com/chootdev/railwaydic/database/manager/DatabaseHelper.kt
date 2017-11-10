package com.chootdev.railwaydic.database.manager

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.chootdev.railwaydic.models.Station
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import java.sql.SQLException

/**
 * Created by Choota on 11/10/17.
 */
class DatabaseHelper(context: Context): OrmLiteSqliteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private var stationDao: Dao<Station, Int>? = null

    companion object {
        private val DATABASE_NAME = "RailwayDic.sqlite"
        private val DATABASE_VERSION = 1
    }

    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        try{
            TableUtils.createTable<Station>(connectionSource, Station::class.java)
        } catch (e: SQLException){
            e.printStackTrace()
        }
    }

    override fun onUpgrade(database: SQLiteDatabase?, connectionSource: ConnectionSource?, oldVersion: Int, newVersion: Int) {
        try{
            TableUtils.dropTable<Station, Any>(connectionSource, Station::class.java, true)
        } catch (e: SQLException){
            e.printStackTrace()
        }
    }

    fun getStationDao(): Dao<Station, Int>? {
        if (null == stationDao) {
            try {
                stationDao = getDao<Dao<Station, Int>, Station>(Station::class.java)
            } catch (e: SQLException) {
                e.printStackTrace()
            }

        }
        return stationDao
    }

    fun deleteStations(){
        try{
            TableUtils.clearTable(connectionSource,Station::class.java)
        } catch (e: SQLException){
            e.printStackTrace()
        }
    }
}