package com.epam.valkaryne.geolocation.room

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Database class return Data Access Object.
 *
 * @author Valentine Litvin
 */

@Database(entities = [MapObject::class], version = 1)
abstract class MapObjectDB : RoomDatabase() {
    abstract fun getMapObjectDAO() : MapObjectDao
}