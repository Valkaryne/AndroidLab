package com.epam.valkaryne.intentmulti

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel

/**
 * ViewModel for keeping necessary lifecycle data: bitmap image and a status of downloading process.
 *
 * @author Valentine Litvin
 */

class MainViewModel : ViewModel() {

    var bitmap: Bitmap? = null
    var isBound = false
    var isDownloading = false
}