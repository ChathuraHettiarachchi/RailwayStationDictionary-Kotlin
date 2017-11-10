package com.chootdev.railwaydic.models

import com.google.gson.annotations.SerializedName

data class StationResponse (
        @SerializedName("array") var array: List<Station>? = null
)