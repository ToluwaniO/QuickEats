package toluog.quickeats.model

data class Order(var name: String = "", var description: String = "", var price: Double = 0.0, var quantity: Int = 0) {
    fun toMap() = mapOf<Any, Any>(
            "name" to name,
            "description" to description,
            "price" to price,
            "quantity" to quantity
    )
}