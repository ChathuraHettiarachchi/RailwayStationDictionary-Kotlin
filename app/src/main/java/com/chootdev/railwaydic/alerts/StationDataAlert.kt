package com.chootdev.railwaydic.alerts

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.chootdev.railwaydic.R
import com.chootdev.railwaydic.R.id.*
import com.chootdev.railwaydic.models.Station

/**
 * Created by Choota on 10/15/17.
 * Email   : chathura93@yahoo.com
 * GitHub  : https://github.com/chathurahettiarachchi
 * Company : Fidenz Technologies
 */

class StationDataAlert(private val context: Context, private val callback: StationDataAlertCallback) {

    private val dialog: Dialog

    init {
        this.dialog = Dialog(context, R.style.ActionSheet)
    }

    fun show(model: Station) {
        dialog.setContentView(R.layout.dialog_station_info)

        (tvName as TextView).text = if (model.stationName != null) model.stationName else "N/A"
        (tvCode as TextView).text = if (model.stationCodeNo != null) model.stationCodeNo else "N/A"
        (tvCodeName as TextView).text = if (model.stationCode != null) model.stationCode else "N/A"
        (tvType as TextView).text = if (model.type != null) model.type else "N/A"
        (tvMiles as TextView).text = if (model.distenceMiles != null) model.distenceMiles else "N/A"
        (tvKm as TextView).text = if (model.distenceKm != null) model.distenceKm else "N/A"
        (tvFeets as TextView).text = if (model.heightFeet != null) model.heightFeet else "N/A"
        (tvMeters as TextView).text = if (model.heightMeters != null) model.heightMeters else "N/A"

        (btnContacat as Button).text = "Contact " + if (model.stationName != null) "\"" + model.stationName + "\" " + model.type else model.type
        (btnContacat as Button).setOnClickListener {
            callback.onCallPressed(model)
            dialog.dismiss()
        }

        (btnCancel as Button).setOnClickListener { dialog.dismiss() }

        val displayMetrics = context.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        val width = (dpWidth.toDouble() * displayMetrics.scaledDensity.toDouble() * 0.8).toInt()

        dialog.show()
        dialog.setCancelable(false)
        dialog.window!!.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#00ffffff")))
    }

    interface StationDataAlertCallback {
        fun onCallPressed(model: Station)
    }
}
