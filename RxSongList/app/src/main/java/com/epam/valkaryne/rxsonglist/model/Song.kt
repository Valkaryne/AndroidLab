package com.epam.valkaryne.rxsonglist.model

/**
 * Data class for songs.
 *
 * @author Valentine Litvin
 */

data class Song(
    val title: String,
    val artist: String,
    val length: String,
    val imgRes: Int
)