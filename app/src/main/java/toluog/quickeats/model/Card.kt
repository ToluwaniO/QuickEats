package toluog.quickeats.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Card(var cardNumber: String = "", var month: Int = 1, var year: Int = 1, var cvc: String = ""): Parcelable