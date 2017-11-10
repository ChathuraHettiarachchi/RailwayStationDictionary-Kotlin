package com.chootdev.railwaydic.helpers

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.os.Handler
import com.chootdev.railwaydic.R
import com.chootdev.railwaydic.database.access.StationAccess
import com.chootdev.railwaydic.models.Station
import com.chootdev.railwaydic.models.StationResponse
import com.google.gson.Gson
import java.util.*

class FillDatabase {

    companion object {
        private var context: Context? = null
        private var fillDatabase: FillDatabase? = null

        private var callback: FillDBCallback? = null
        private var dialog: ProgressDialog? = null
        private var isDBCreated: Boolean = false
        private var stationAccess: StationAccess? = null

        fun sendData(datalist: List<Station>?) {
            dialog!!.setMessage("Populating...")
            Handler().postDelayed({
                dialog!!.dismiss()
                callback!!.onPopulateFinished(datalist != null, datalist)
            }, 1000)
        }
    }

    fun init(appContext: Context, appCallback: FillDBCallback): FillDatabase {
        if (fillDatabase == null)
            fillDatabase = FillDatabase()

        context = appContext
        callback = appCallback

        dialog = ProgressDialog(context)
        dialog!!.setCancelable(false)

        stationAccess = StationAccess(appContext)
        isDBCreated = UserDefaults.isDBUpdated(context!!)

        return fillDatabase as FillDatabase
    }

    fun requestDataPopulation() {
        if (!isDBCreated)
            dialog!!.setMessage("Initializing...")
        else
            dialog!!.setMessage("Loading...")

        dialog!!.show()
        SetupDBWithDataAsync().execute()
    }

    interface FillDBCallback {
        fun onPopulateFinished(status: Boolean, stations: List<Station>?)
    }

    private class SetupDBWithDataAsync : AsyncTask<Void, Void, List<Station>>() {

        override fun doInBackground(vararg params: Void): List<Station> {
            dialog!!.setMessage("Wait little more...")

            var dataList: MutableList<Station> = ArrayList<Station>()

            if (isDBCreated) {
                dataList = stationAccess?.findAll() as MutableList<Station>
            } else {
                val dataJSON: String = ReadJSON.jsonFileToString(context!!.resources.openRawResource(R.raw.data))
                val dataResponce: StationResponse? = Gson().fromJson(dataJSON, StationResponse::class.java)

                stationAccess?.deleteAll()
                if (dataResponce != null) {
                    for (st in dataResponce.array!!) {
                        dataList.add(st)
                        stationAccess?.create(st)
                    }
                }

                UserDefaults.updateInit(context!!, true)
            }

            return dataList
        }

        override fun onPostExecute(data: List<Station>) {
            super.onPostExecute(data)
            sendData(data)
        }
    }
}
