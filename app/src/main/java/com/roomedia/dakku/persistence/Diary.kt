package com.roomedia.dakku.persistence

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.Date

@Entity
data class Diary(
    @PrimaryKey val id: Long = Date().time,
    val title: String = id.toString(),
    var bookmark: Boolean = false,
    var lock: Boolean = false,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    )

    fun toCalendar(): Calendar = Calendar.getInstance().also {
        it.time = Date(id)
    }

    fun isInSameWeek(that: Diary): Boolean {
        return this.toCalendar().get(Calendar.WEEK_OF_YEAR) ==
            that.toCalendar().get(Calendar.WEEK_OF_YEAR)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeByte(if (bookmark) 1 else 0)
        parcel.writeByte(if (lock) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Diary> {
        override fun createFromParcel(parcel: Parcel): Diary {
            return Diary(parcel)
        }

        override fun newArray(size: Int): Array<Diary?> {
            return arrayOfNulls(size)
        }
    }
}
