package com.example.audiomessage

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity(builder: Any) : AppCompatActivity() {
    val  titleMessageAlert:String = "Разрешение на доступ к памяти телефона"
    val  bodyMessageAlert:String = "Для вашего доступа к музыкальным файлам требуется разрешение на чтение памяти телефона."
    val btnAlert:String = "ПРИНЯТЬ"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Считываем в каком состоянии находиться разрешение на чтение памяти
        val permissionStatus:Int = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        //Проверяем,если не подходит, то делаем запрос на разрешение
        if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        } else {
            init()
        }
    }
    fun init() {
        //инициализируем recyclerView
        val recyclerView:RecyclerView = findViewById(R.id.recycler_view_item_music)
        val emptyView:TextView = findViewById(R.id.no_active_jobs)
        val layoutManager = LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //создаем презентер
        val userPresenter = UserPresenterImpl();
        //Считываем лист(название муз.файлов),если равен 0, то делаем невидимым RecyclerView и видимым textView("Файлов нет!")
        if (userPresenter.showListMusic().size == 0) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            //отправояем команду презентору чтобы тот вернул список файлов и отправляем все в адаптер, чтоб отобразить на экране
            val itemMusicFileAdapter =  ItemMusicFileAdapter(userPresenter.showListMusic())
            recyclerView.setAdapter(itemMusicFileAdapter)
        }
    }
/*Переопределяем метод проверки разрешений.
 * Так как SDK_VERSION < 23 проверка происходит при установке приложения, то будем проверять в версия от 23 и выше.
 * Если разрешение на чтение памяти не получено, то вызываем shouldShowRequestPermissionRationale()
 * shouldShowRequestPermissionRationale()  true, если пользователь ранее уже отклонял запрос на предоставление разрешения
 * false если запрос разрешения происходит впервые или если пользователь в ответ на прежний запрос выставил опцию Don't ask again в диалоговом окне запроса
 * Если true то вызовем диалог где уточним пользователю зачем нужен доступ к памяти
 * Если false то откроем настройки приложения
 * Иначе если доступ к памяти получен, идем дальше*/

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                ) {
                    //showAlertDialog()
                } else {
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri = Uri.fromParts("package", this.packageName, null)
                    intent.data = uri
                    this.startActivity(intent)
                }
            } else {
                init()
            }
        }
    }

//Метод вызова диалога, где показано, зачем нужен доступ к памяти
/*private fun showAlertDialog() {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(titleMessageAlert).setMessage(bodyMessageAlert).setNegativeButton(btnAlert)
    { dialogInterface: DialogInterface, i: Int ->
        dialogInterface.cancel()
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                100
            )
        }
    val alert = builder.create()
    alert.show();
}*/

}




