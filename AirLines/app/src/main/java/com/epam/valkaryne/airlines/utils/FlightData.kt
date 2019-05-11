package com.epam.valkaryne.airlines.utils

import android.os.Parcelable
import android.os.Parcel

/**
 * Data class for data will displayed on the screen.
 *
 * @author Valentine Litvin
 */

data class FlightData(val depart: String?,
                      val arrival: String?,
                      val date: String?,
                      val price: Int,
                      val takeoffTime: String?,
                      val landingTime: String?,
                      val duration: String?,
                      val freeSeats: Int,
                      val currency: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(depart)
        parcel.writeString(arrival)
        parcel.writeString(date)
        parcel.writeInt(price)
        parcel.writeString(takeoffTime)
        parcel.writeString(landingTime)
        parcel.writeString(duration)
        parcel.writeInt(freeSeats)
        parcel.writeString(currency)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FlightData> {
        override fun createFromParcel(parcel: Parcel): FlightData {
            return FlightData(parcel)
        }

        override fun newArray(size: Int): Array<FlightData?> {
            return arrayOfNulls(size)
        }
    }
}