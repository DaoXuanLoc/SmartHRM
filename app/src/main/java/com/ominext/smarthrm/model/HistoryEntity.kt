package com.ominext.smarthrm.model

import com.google.gson.annotations.SerializedName

class HistoryEntity {

    @SerializedName("date")
    var date: String? = null

    @SerializedName("checkIn")
    var checkIn: String? = null

    @SerializedName("checkOut")
    var checkOut: String? = null
}