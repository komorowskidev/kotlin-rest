package pl.komorowskidev.kotlinrest.db.services

import pl.komorowskidev.kotlinrest.db.entities.Entity

interface Service {

    fun save(entity: Entity)
}