package com.example

import java.io.File
import java.nio.file.Files
class FileTraversal {
    fun showAllFiles(dir : String): String {
        val files: ArrayList<File> = ArrayList()
        val stringBuilder = StringBuilder()
        File("./$dir/").walk().forEach {
            files.add(it)
            stringBuilder.append(it.name)
            stringBuilder.append("\n")
        }
        return stringBuilder.toString()
    }
}