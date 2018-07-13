package toluog.quickeats.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Restaurant(var id: String = "", var name: String = "", var imageUrl: String = ""):Parcelable
