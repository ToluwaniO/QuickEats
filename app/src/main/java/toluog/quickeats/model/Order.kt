package toluog.quickeats.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(var name: String = "", var description: String = "", var price: Double = 0.0,
                 var quantity: Int = 0, var notes: String = ""): Parcelable {
    fun toMap() = mapOf<Any, Any>(
            "name" to name,
            "description" to description,
            "price" to price,
            "quantity" to quantity,
            "notes" to notes
    )
}