package service

import com.mongodb.rx.client.Success
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import dao.ItemDao
import entity.Currency
import entity.Item
import entity.convert
import rx.Observable

class ItemService : KoinComponent {
    private val dao by inject<ItemDao>()

    fun add(item: Item): Observable<Success> {
        return dao.add(item)
    }

    fun all(currency: Currency): Observable<Item> {
        return dao.all()
            .map { item ->
                Item(item.name, item.price.convert(item.currency, currency), currency)
            }
    }
}
