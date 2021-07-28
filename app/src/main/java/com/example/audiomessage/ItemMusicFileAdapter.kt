package com.example.audiomessage

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import java.io.File


class ItemMusicFileAdapter : RecyclerView.Adapter<ItemMusicFileAdapter.ViewHolder> {


    var items: List<String>
    val MESSAGE = "\nФайл скачан с очень интересного сайта";
    lateinit var nameFileMusic: TextView
    val path: String = android.os.Environment.DIRECTORY_DOWNLOADS

    constructor(items: List<String>) {
        this.items = items
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameFileMusic = itemView.findViewById<TextView>(R.id.name_music_file)
    }


    override fun onCreateViewHolder(parent: ViewGroup, index: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.items, parent, false)
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, index: Int) {
        nameFileMusic.setText(String.format("%s", this.items.get(index)))
        holder.itemView.setOnClickListener(View.OnClickListener { v: View ->
            val file = File(path + items.get(index))
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "audio/*"
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            val uri =
                FileProvider.getUriForFile(v.context, "com.example.mp3message.provider", file)
            intent.putExtra(Intent.EXTRA_TEXT, MESSAGE)
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            try {
                holder.itemView.getContext().startActivity(Intent.createChooser(intent, MESSAGE))
            } catch (e: Exception) {
                Toast.makeText(v.context, "Не отправилось!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun getItemCount(): Int {
        return items.size
    }


}



