package com.ominext.smarthrm.model

class History {

    var date: String? = null
    var checkIn: String? = null
    var checkOut: String? = null

    companion object {
        fun convertFromEntity(entity: HistoryEntity): History {
            return History().apply {
                this.date = entity.date
                this.checkIn = entity.checkIn
                this.checkOut = entity.checkOut
            }
        }
    }
}