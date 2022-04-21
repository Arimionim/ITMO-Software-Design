package bson

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import de.undercouch.bson4jackson.BsonFactory
import domain.EventType
import domain.event.ExtendSubEvent
import domain.event.IssueSubEvent
import domain.event.VisitEvent
import org.bson.Document

object Serializer {
    val jackson: ObjectMapper = ObjectMapper(BsonFactory())
        .findAndRegisterModules()
        .apply {
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        }

    fun decodeDocument(document: Document) = when (EventType.valueOf(document.get("type", String::class.java))) {
        EventType.EXTEND_SUB -> jackson.convertValue<ExtendSubEvent>(document)
        EventType.ISSUE_SUB -> jackson.convertValue<IssueSubEvent>(document)
        EventType.VISIT_EVENT -> jackson.convertValue<VisitEvent>(document)
    }
}
