package com.example.audiomessage

import android.os.Environment
import android.os.Environment.getExternalStorageDirectory

//Считываем директорию, потом убираем лишние файлы(не audio)
class UserPresenterImpl:UserPresenter {
    val path:String = Environment.getExternalStorageDirectory().toString() + "/Download"
    override fun showListMusic(): List<String> {

        val userModel = UserModel()
        val deleteNotAudio = DeleteNotAudio()
        return deleteNotAudio.accept(userModel.readDir(path));
    }

}