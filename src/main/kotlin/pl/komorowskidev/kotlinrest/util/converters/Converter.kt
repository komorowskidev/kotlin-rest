package pl.komorowskidev.kotlinrest.util.converters

import pl.komorowskidev.kotlinrest.db.entities.Entity

interface Converter {

    fun dtoToEntity(line: String): Entity?
}