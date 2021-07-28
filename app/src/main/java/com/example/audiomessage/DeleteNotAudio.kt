package com.example.audiomessage

import java.io.File
import java.net.URLConnection
import java.util.ArrayList

//Узнает является ли тип MIME == audio
class DeleteNotAudio {
    lateinit private var mimeType: String

    fun accept(str: List<File>): List<String> {
        var result: ArrayList<String> = ArrayList()
        for (file in str) {
            mimeType = URLConnection.guessContentTypeFromName(file.name)
            if (mimeType != null && mimeType.indexOf("audio") == 0) {
                result.add(file.name)
            }
        }
        return result
    }
}