package com.epam.valkaryne.imagepicker

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

/**
 * The main activity of the application with simplified implementation.
 * It handles the only onClickListener for the <code>btnGallery</code> button.
 * The button performs opening of mobile gallery and suggests user to choose an image or a photo
 * to put it on the main screen then.
 *
 * @author Valentine Litvin
 */

class MainActivity : AppCompatActivity() {

    private var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            bitmap = savedInstanceState.getParcelable(IMAGE_SAVED_KEY) as Bitmap
            bitmap?.let { ivImage.setImageBitmap(it) }
        }

        btnGallery.setOnClickListener { navigateToGallery() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            IMAGE_PICKED_REQUEST -> {
                if (resultCode == Activity.RESULT_OK)
                    resolveImageData(data)
                else
                    Toast.makeText(applicationContext, getString(R.string.get_image_error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(IMAGE_SAVED_KEY, bitmap)
    }

    private fun navigateToGallery() {
        val galleryIntent = Intent()
        galleryIntent.action = Intent.ACTION_PICK
        galleryIntent.type = IMAGE_INTENT_TYPE
        startActivityForResult(galleryIntent, IMAGE_PICKED_REQUEST)
    }

    private fun resolveImageData(data: Intent?) {
        if (data != null) {
            val pickedImage = data.data
            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, pickedImage)
            ivImage.setImageBitmap(bitmap)
        } else {
            Toast.makeText(applicationContext, getString(R.string.get_image_error), Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val IMAGE_PICKED_REQUEST = 1101
        private const val IMAGE_INTENT_TYPE = "image/*"
        private const val IMAGE_SAVED_KEY = "image"
    }
}
