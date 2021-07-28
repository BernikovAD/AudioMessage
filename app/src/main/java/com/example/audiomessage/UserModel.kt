package com.example.audiomessage

import java.io.File
import java.util.*
import kotlin.collections.ArrayList

//Считываю данные из директории
class UserModel {
    //Читаем директорию и заполняем List<File>
    fun readDir(path: String?): List<File> {
        val directory = File(path)
        val listMusicName: MutableList<File> = ArrayList<File>();
        val files = directory.listFiles()
        if (files != null) listMusicName.addAll(Arrays.asList(*files))
        return listMusicName;
    }
}