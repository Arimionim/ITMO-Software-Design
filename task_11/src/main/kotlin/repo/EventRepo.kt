package repo

import bson.Serializer
import bson.Serializer.jackson
import com.fasterxml.jackson.module.kotlin.convertValue
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import domain.event.Event
import org.bson.conversions.Bson
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EventRepo : KoinComponent {
    private val database by inject<MongoDatabase>()
    private val collection by lazy { database.getCollection("gym") }

    fun add(event: Event) {
        collection.insertOne(jackson.convertValue(event))
    }

    fun find(filter: Bson): List<Event> {
        return collection.find(filter)
            .toList()
            .map(Serializer::decodeDocument)
    }

    fun clean() {
        collection.deleteMany(Filters.empty())
    }
}
