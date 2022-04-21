package dao

import com.mongodb.rx.client.MongoClient
import com.mongodb.rx.client.MongoCollection
import com.mongodb.rx.client.Success
import org.bson.Document
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import entity.Item
import rx.Observable

class ItemDao : KoinComponent {
    private val client by inject<MongoClient>()
    private val database = client.getDatabase("default")

    private fun getCollection(): MongoCollection<Document> =
        database.getCollection(Item.COLLECTION_NAME)

    fun add(item: Item): Observable<Success> = getCollection()
        .insertOne(item.toDocument())

    fun all(): Observable<Item> = getCollection()
        .find()
        .toObservable()
        .map(Item::fromDocument)
}
