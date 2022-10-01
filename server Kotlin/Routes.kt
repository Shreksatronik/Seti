package com.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.FileTraversal
import com.example.ReadFiles


fun Route.customerRouting() {

    route(""){
        var name : String
        get{
            name = call.parameters["name"].toString()
            call.respondText("Hello, $name", status = HttpStatusCode.OK)
        }
    }

    route("/{dir}") {
        get {
            val fileTraversal = FileTraversal()
            val res = fileTraversal.showAllFiles(call.parameters["dir"].toString())
            if (res == "") {
                call.respondText("No such directory", status = HttpStatusCode.NotFound)
            } else {
                call.respondText(res, status = HttpStatusCode.OK)
            }
        }
    }

    route("/{dir}/{filename}") {
        get {
            val readFiles = ReadFiles()
            val res: String =
                readFiles.readFile(call.parameters["dir"].toString(), call.parameters["filename"].toString())
            if (res == "No such file or directory") {
                call.respondText(res, status = HttpStatusCode.NotFound)
            } else {
                call.respondText(res, status = HttpStatusCode.OK)
            }
        }
    }
}