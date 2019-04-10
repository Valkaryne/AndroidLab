package com.epam.valkaryne.imagepicker

import android.app.Activity
import android.content.Intent
import android.net.Uri
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

    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            imageUri = savedInstanceState.getParcelable(IMAGE_SAVED_KEY) as Uri
            imageUri?.let { updateImage(it) }
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
        outState?.putParcelable(IMAGE_SAVED_KEY, imageUri)
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
            pickedImage?.let { updateImage(it) }
        } else {
            Toast.makeText(applicationContext, getString(R.string.get_image_error), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateImage(uri: Uri) {
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
        ivImage.setImageBitmap(bitmap)
    }

    private companion object {
        private const val IMAGE_PICKED_REQUEST = 1101
        private const val IMAGE_INTENT_TYPE = "image/*"
        private const val IMAGE_SAVED_KEY = "image"
    }
}
