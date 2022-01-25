package com.thurmann.technicaltest.service.file

import java.io.File

class FileReader(val path: String) {

    private fun getResourceAsFile(): File {
        return File(javaClass.classLoader.getResource(path).file)
    }
    
    private fun getFileFromPath() =
        File(path)

    fun readAsStringFile() =
        getFileFromPath().readLines()

    fun readDelimitedFile(delimiter: String, removeWhitespace: Boolean) =
        readAsStringFile().map { it ->
            it.split(delimiter).filterNot { it.all { char -> removeWhitespace && char.isWhitespace() } }
        }
}