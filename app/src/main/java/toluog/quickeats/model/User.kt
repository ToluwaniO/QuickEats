package toluog.quickeats.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(var uid: String = "", var email: String = "", var displayName: String = ""): Parcelable {
    fun toMap() = mapOf<Any,Any>(
            "uid" to uid,
            "email" to email,
            "displayName" to displayName
    )
}