package com.epam.valkaryne.painttester

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get

import com.epam.valkaryne.painttester.utils.*

/**
 * Custom view. It has [SeekBar] with [TextView] indicator and colorpicker based on RadioGroup.
 * Widget has three attributes:
 *  @property pMaxWidth defines max value of the [SeekBar]
 *  @property defaultColorPosition chooses checked color initially
 *  @property firstItemColor sets custom color
 *
 * @author Valentine Litvin
 */

class PaintWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var pMaxWidth = 0
        set(value) {
            field = value
            operateMaxWidth()
        }
    var defaultColorPosition = 0
        set(value) {
            field = value % 4
            operateDefaultColorPosition()
        }
    var firstItemColor: Int? = null
        set(value) {
            field = value
            operateFirstItemColor()
        }

    var hexColor = "#000000"
        private set

    var mOnPaintWidgetChangeListener: OnPaintWidgetChangeListener? = null

    private var view: View? = null
    private var sbValue: SeekBar? = null
    private var tvWidth: TextView? = null
    private var rgColor: RadioGroup? = null

    init {
        view = View.inflate(context, R.layout.widget_paint, this)

        val a = context.obtainStyledAttributes(attrs, R.styleable.PaintWidget)
        pMaxWidth = a.getInteger(R.styleable.PaintWidget_maxWidth, 10)
        defaultColorPosition = a.getInteger(R.styleable.PaintWidget_defaultColorPosition, 0)
        firstItemColor = a.getColor(R.styleable.PaintWidget_firstItemColor, Color.BLACK)

        operateFirstItemColor()

        sbValue = view?.findViewById(R.id.sb_value)
        tvWidth = view?.findViewById(R.id.tv_width)
        rgColor = view?.findViewById(R.id.rg_colors)

        sbValue?.setOnSeekBarChangeListener(SeekBarListener())

        operateMaxWidth()
        operateDefaultColorPosition()

        rgColor?.setOnCheckedChangeListener(RadioGroupListener())

        a.recycle()
    }

    private fun operateFirstItemColor() {
        val rbCustom = view?.findViewById<CustomRadioButton>(R.id.rb_custom)
        rbCustom?.color = firstItemColor
    }

    private fun operateMaxWidth() {
        sbValue?.max = pMaxWidth

        val onefifth = Math.ceil(0.2 * pMaxWidth).toInt()
        sbValue?.progress = onefifth
    }

    private fun operateDefaultColorPosition() {
        val rbDefault = rgColor?.get(defaultColorPosition)
        if (rbDefault is CustomRadioButton) {
            rbDefault.isChecked = true
        }
    }

    interface OnPaintWidgetChangeListener {
        fun onWidthChanged(width: Int)

        fun onColorChanged(color: String)
    }

    inner class SeekBarListener : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            tvWidth?.text = progress.toString()
            mOnPaintWidgetChangeListener?.onWidthChanged(progress)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }
    }

    inner class RadioGroupListener : RadioGroup.OnCheckedChangeListener {
        override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
            val radioButton = view?.findViewById<CustomRadioButton>(checkedId)
            val color = radioButton?.color
            hexColor = getHexColor(color)

            mOnPaintWidgetChangeListener?.onColorChanged(hexColor)
        }
    }
}