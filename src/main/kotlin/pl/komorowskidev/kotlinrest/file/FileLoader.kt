package pl.komorowskidev.kotlinrest.file

import org.springframework.stereotype.Component
import pl.komorowskidev.kotlinrest.InitDataProcessor
import pl.komorowskidev.kotlinrest.db.services.Service
import pl.komorowskidev.kotlinrest.util.converters.Converter
import java.io.File
import java.io.IOException

@Component
class FileLoader(private val initDataProcessor: InitDataProcessor) {

    @Throws(IOException::class)
    fun load(pathName: String, converter: Converter, service: Service) {
        File(pathName).forEachLine { initDataProcessor.convertAndInsertIntoDatabase(converter, service, it) }
    }

}
