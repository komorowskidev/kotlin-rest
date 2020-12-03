package pl.komorowskidev.kotlinrest.db.services

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

@Component
class DbService(private val mongoTemplate: MongoTemplate) {

    fun clearDataBase() {
        mongoTemplate.db.drop()
    }

    fun removeTemporaryCollection(collectionName: String) {
        mongoTemplate.db.getCollection(collectionName).drop()
    }
}