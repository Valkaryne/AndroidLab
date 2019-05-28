package com.epam.valkaryne.rxsonglist.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.epam.valkaryne.rxsonglist.R
import com.epam.valkaryne.rxsonglist.model.Song

class SongsAdapter : RecyclerView.Adapter<SongsAdapter.ViewHolder>() {

    private val songs: MutableList<Song> = ArrayList()

    fun addItem(song: Song) {
        Log.d("SuperCat", "addItem")
        songs.add(song)
        notifyDataSetChanged()
    }

    fun clearItems() {
        songs.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("SuperCat", "onCreateViewHolder")
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_song, parent, false) as CardView
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("SuperCat", "position $position")
        val song = songs[position]
        val itemView = holder.item

        val tvTitle = itemView.findViewById<TextView>(R.id.tv_card_title)
        val tvArtist = itemView.findViewById<TextView>(R.id.tv_card_artist)
        val tvLength = itemView.findViewById<TextView>(R.id.tv_card_length)
        val ivAlbum = itemView.findViewById<ImageView>(R.id.iv_card_album)

        tvTitle.text = song.title
        tvArtist.text = song.artist
        tvLength.text = song.length
        ivAlbum.setImageResource(song.imgRes)
    }

    override fun getItemCount() = songs.size

    inner class ViewHolder(val item: CardView) : RecyclerView.ViewHolder(item)
}