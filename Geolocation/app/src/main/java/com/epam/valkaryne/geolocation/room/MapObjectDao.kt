package com.epam.valkaryne.geolocation.room

import androidx.room.*

/**
 * Data Access Object for map objects.
 *
 * @author Valentine Litvin
 */

@Dao
interface MapObjectDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insert(mapObject: MapObject)

    @Delete
    fun delete(mapObject: MapObject)

    @Query("DELETE FROM mapObjects WHERE uid = :uid")
    fun deleteById(uid: String)

    @Query("SELECT * FROM mapObjects WHERE uid = :uid")
    fun getMapObject(uid: String) : MapObject
}