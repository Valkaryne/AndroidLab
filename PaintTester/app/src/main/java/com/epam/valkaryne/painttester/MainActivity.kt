package com.epam.valkaryne.painttester

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.ToggleButton
import androidx.core.view.isVisible

/**
 * Simple Activity for testing the [PaintWidget]. It contains one [ToggleButton] which shows/hide
 * our Custom View.
 * There is one Listener declared in the code listens to color and width params are changed.
 *
 * @author Valentine Litvin
 */
class MainActivity : AppCompatActivity() {

    private var widget: PaintWidget? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        widget = findViewById(R.id.paintWidget)
        val tButton = findViewById<ToggleButton>(R.id.toggleButton)
        tButton.setOnCheckedChangeListener { _, isChecked -> widget?.isVisible = isChecked }

        widget?.mOnPaintWidgetChangeListener = PaintWidgetListener()
        widget?.firstItemColor = Color.rgb(0, 168, 107)
        widget?.pMaxWidth = 100
        widget?.defaultColorPosition = 2
    }

    inner class PaintWidgetListener : PaintWidget.OnPaintWidgetChangeListener {

        private var color: String? = null
        private var width: Int? = null

        override fun onColorChanged(color: String) {
            this.color = color
            if (this.color != null && width != null)
                Toast.makeText(applicationContext, "Color: ${this.color}, Width: $width", Toast.LENGTH_SHORT).show()
        }

        override fun onWidthChanged(width: Int) {
            this.width = width
            if (color != null && this.width != null)
                Toast.makeText(applicationContext, "Color: $color, Width: ${this.width}", Toast.LENGTH_SHORT).show()
        }
    }
}
