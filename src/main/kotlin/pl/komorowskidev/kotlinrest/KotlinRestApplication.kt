package pl.komorowskidev.kotlinrest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class KotlinRestApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>){
            runApplication<KotlinRestApplication>(*args)
        }
    }
}

