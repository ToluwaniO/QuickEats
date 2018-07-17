package toluog.quickeats.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Table(var id: String = "", var restaurantId: String = "", var isOccupied: Boolean = false,
                 var total: Double = 0.0, var occupants: ArrayList<User> = arrayListOf(),
                 var orders: ArrayList<Order> = arrayListOf()): Parcelable
