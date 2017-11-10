package com.chootdev.railwaydic.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.chootdev.railwaydic.R
import com.chootdev.railwaydic.alerts.StationDataAlert
import com.chootdev.railwaydic.helpers.SnacManager
import com.chootdev.railwaydic.models.Station
import java.util.*

/**
 * Created by Choota on 10/15/17.
 * Email   : chathura93@yahoo.com
 * GitHub  : https://github.com/chathurahettiarachchi
 * Company : Fidenz Technologies
 */

class StationListAdapter(private val context: Context, private val callback: StationFilterCallback, private var orig: List<Station>?) : RecyclerView.Adapter<StationListAdapter.Holder>(), StationDataAlert.StationDataAlertCallback {
    private val stationDataAlert: StationDataAlert
    private var items: List<Station>? = null

    val filter: Filter
        get() = object : Filter() {
            override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
                val oReturn = Filter.FilterResults()
                val results = ArrayList<Station>()
                if (orig == null)
                    orig = items
                if (constraint != null) {
                    if (orig != null && orig!!.size > 0) {
                        for (g in orig!!) {
                            if (g.stationName!!.toLowerCase().contains(constraint.toString().toLowerCase()))
                                results.add(g)
                        }
                    }
                    oReturn.values = results
                }
                return oReturn
            }

            override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
                items = results.values as List<Station>

                if (items == null || items!!.size == 0)
                    callback.onStationFilter(false)
                else
                    callback.onStationFilter(true)

                notifyDataSetChanged()
            }
        }

    init {
        this.items = orig
        this.stationDataAlert = StationDataAlert(context, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(context).inflate(R.layout.widget_station, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = items!![position]

        holder.name.text = if (item.stationName != null) item.stationName else "N/A"
        holder.nameCode.text = "Name: " + if (item.stationName != null) item.stationName else "N/A"
        holder.code.text = "Code: " + if (item.stationCodeNo != null) item.stationCodeNo else "N/A"
        holder.btnCall.setBackgroundResource(if (item.telephone != null) R.drawable.icon_call else R.drawable.icon_call_dis)
        holder.btnCall.setOnClickListener { tryToMakeCall(item) }

        holder.mainView.setOnClickListener { stationDataAlert.show(item) }
    }

    private fun tryToMakeCall(item: Station) {
        try {
            val phone = item.telephone
            if (phone != null && phone!!.length != 0) {
                makeCall(phone, (if (item.stationName != null) item.stationName else "")!!)
            } else {
                var message = SnacManager()
                message.makeMessage(context, 2, "No number found...")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @SuppressLint("MissingPermission")
    private fun makeCall(number: String, stationName: String) {

        var message = SnacManager()
        message.makeMessage(context, 1, "Calling $stationName station...")

        Handler().postDelayed({
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:" + number)
            context.startActivity(intent)
        }, 500)

    }

    override fun getItemCount(): Int {
        return if (items != null) items!!.size else 0
    }

    override fun onCallPressed(model: Station) {
        tryToMakeCall(model)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView
        var nameCode: TextView
        var code: TextView
        var btnCall: ImageButton
        var mainView: LinearLayout

        init {
            name = itemView.findViewById<View>(R.id.tvStationName) as TextView
            nameCode = itemView.findViewById<View>(R.id.tvStationCodeName) as TextView
            code = itemView.findViewById<View>(R.id.tvStationCode) as TextView
            btnCall = itemView.findViewById<View>(R.id.btnCall) as ImageButton
            mainView = itemView.findViewById<View>(R.id.layMain) as LinearLayout
        }
    }

    interface StationFilterCallback {
        fun onStationFilter(isHavingResults: Boolean)
    }
}
