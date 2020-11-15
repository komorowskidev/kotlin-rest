package pl.komorowskidev.kotlinrest.file

import org.springframework.stereotype.Component
import java.io.File

@Component
class FileHelper {

    fun getLines(pathName: String): List<String> {
        val result = ArrayList<String>()
        val file = File(pathName)
        if (file.exists()) {
            file.forEachLine { result.add(it) }
        }
        return result
    }
}