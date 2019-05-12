package com.epam.valkaryne.geolocation.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

/**
 * Class with static util functions for work with interactive map.
 *
 * @author Valentine Litvin
 */

class MapUtils {
    companion object {
        fun createMarkerItemFromDrawable(drawable: Drawable?): BitmapDescriptor? {
            drawable?.let {
                val canvas = Canvas()
                val bitmap = Bitmap.createBitmap(
                    it.intrinsicWidth,
                    it.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
                )
                canvas.setBitmap(bitmap)
                it.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight)
                it.draw(canvas)
                return BitmapDescriptorFactory.fromBitmap(bitmap)
            }
            return null
        }
    }
}