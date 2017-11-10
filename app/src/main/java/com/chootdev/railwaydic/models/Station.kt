package com.chootdev.railwaydic.models

import com.google.gson.annotations.SerializedName
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

/**
 * Created by Choota on 11/10/17.
 */

@DatabaseTable(tableName = "Station")
class Station {

    @DatabaseField(generatedId = true)
    var id: Int = 0

    @DatabaseField
    @SerializedName("station_name")
    var stationName: String? = null

    @DatabaseField
    @SerializedName("distence_miles")
    var distenceMiles: String? = null

    @DatabaseField
    @SerializedName("height_feet")
    var heightFeet: String? = null

    @DatabaseField
    @SerializedName("height_meters")
    var heightMeters: String? = null

    @DatabaseField
    @SerializedName("distence_km")
    var distenceKm: String? = null

    @DatabaseField
    @SerializedName("station_code")
    var stationCode: String? = null

    @DatabaseField
    @SerializedName("telephone")
    var telephone: String? = null

    @DatabaseField
    @SerializedName("type")
    var type: String? = null

    @DatabaseField
    @SerializedName("station_code_no")
    var stationCodeNo: String? = null

    constructor() {}

    constructor(id: Int, stationName: String?, distenceMiles: String?, heightFeet: String?, heightMeters: String?, distenceKm: String?, stationCode: String?, telephone: String?, type: String?, stationCodeNo: String?) {
        this.id = id
        this.stationName = stationName
        this.distenceMiles = distenceMiles
        this.heightFeet = heightFeet
        this.heightMeters = heightMeters
        this.distenceKm = distenceKm
        this.stationCode = stationCode
        this.telephone = telephone
        this.type = type
        this.stationCodeNo = stationCodeNo
    }

}