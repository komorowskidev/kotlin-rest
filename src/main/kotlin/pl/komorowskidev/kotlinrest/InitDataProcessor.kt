package pl.komorowskidev.kotlinrest

import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.db.services.Service
import pl.komorowskidev.kotlinrest.util.converters.Converter

@Component
class InitDataProcessor {

    fun convertAndInsertIntoDatabase(converter: Converter, service: Service, line: String){
        val entity = converter.dtoToEntity(line)
        if (entity != null) {
            service.save(entity)
        }
    }
}