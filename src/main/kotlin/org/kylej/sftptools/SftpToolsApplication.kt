package org.kylej.sftptools

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SftpToolsApplication

fun main(args: Array<String>) {
    runApplication<SftpToolsApplication>(*args)
}
