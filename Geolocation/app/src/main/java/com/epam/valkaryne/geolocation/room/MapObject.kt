package com.epam.valkaryne.geolocation.room

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Database class stores coordinates of the certain map object (e.g. target marker).
 *
 * @author Valentine Litvin
 */

@Entity(tableName = "mapObjects")
data class MapObject(@PrimaryKey val uid: String,
                     val latitude: Double,
                     val longitude: Double)