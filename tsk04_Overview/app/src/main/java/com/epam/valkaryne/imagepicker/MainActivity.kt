package com.epam.valkaryne.imagepicker

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val IMAGE_PICKED_REQUEST = 1101
    }

    private var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            bitmap = savedInstanceState.getParcelable("image") as Bitmap
            iv_image.setImageBitmap(bitmap)
        }

        btn_gallery.setOnClickListener { navigateToGallery() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            IMAGE_PICKED_REQUEST -> resolveImageData(resultCode, data)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable("image", bitmap)
    }

    private fun navigateToGallery() {
        val galleryIntent = Intent()
        galleryIntent.action = Intent.ACTION_PICK
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, IMAGE_PICKED_REQUEST)
    }

    private fun resolveImageData(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            val pickedImage = data.data
            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, pickedImage)
            iv_image.setImageBitmap(bitmap)
        } else {
            Toast.makeText(applicationContext, "Error while opening image", Toast.LENGTH_SHORT).show()
        }
    }
}
