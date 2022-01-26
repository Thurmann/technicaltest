package com.thurmann.technicaltest.service.file

import com.thurmann.technicaltest.model.util.removeWhiteSpaces
import java.io.File

class FileReader(val path: String) {

    private fun getFileFromPath() =
        File(path)

    fun readAsStringFile() =
        getFileFromPath().readLines()

    fun readDelimitedFile(delimiter: String, removeWhitespace: Boolean) =
        readAsStringFile().map { it.removeWhiteSpaces() }.map { it.split(delimiter) }
    
    fun readSingleLineDelimitedFile(delimiter: String, removeWhitespace: Boolean): List<String> {
        val listOfList = readDelimitedFile(delimiter, removeWhitespace)
        return listOfList[0]
    }
}