package entity

import org.bson.Document

data class Item(
    val name: String,
    val price: Double,
    val currency: Currency
) {
    var id: String = ""

    companion object {
        const val COLLECTION_NAME = "item"

        const val ID = "_id"
        const val NAME = "name"
        const val PRICE = "price"
        const val CURRENCY = "currency"

        fun fromDocument(document: Document) = Item(
            document.getString(NAME),
            document.getDouble(PRICE),
            Currency.valueOf(document.getString(CURRENCY))
        ).also {
            it.id = document[ID].toString()
        }
    }

    fun toDocument(): Document = Document().apply {
        if (id.isNotEmpty()) set(ID, id)
        set(NAME, name)
        set(PRICE, price)
        set(CURRENCY, currency.toString())
    }
}
