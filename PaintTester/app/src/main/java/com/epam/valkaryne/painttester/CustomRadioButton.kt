package com.epam.valkaryne.painttester

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatRadioButton

class CustomRadioButton(context: Context?, attrs: AttributeSet?) :
    AppCompatRadioButton(context, attrs) {

    var color: Int? = null

    private var alphaColor: Int? = null
    private var picker: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var marker: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        val a = context?.obtainStyledAttributes(attrs, R.styleable.CustomRadioButton)
        color = a?.getColor(R.styleable.CustomRadioButton_color, Color.BLACK)

        color?.let {
            val alpha: Int = Math.round(Color.alpha(it) * 0.5).toInt()
            alphaColor = Color.argb(alpha, Color.red(it), Color.green(it), Color.blue(it))
        }
        a?.recycle()
        initPaint()
    }

    private fun initPaint() {
        val standardColor = Color.BLACK
        val alpha: Int = Math.round(Color.alpha(standardColor) * 0.5).toInt()

        picker = Paint(Paint.ANTI_ALIAS_FLAG)
        picker.style = Paint.Style.STROKE
        picker.color = standardColor

        marker = Paint(Paint.ANTI_ALIAS_FLAG)
        marker.style = Paint.Style.STROKE
        marker.color = Color.argb(alpha, Color.red(standardColor), Color.green(standardColor), Color.blue(standardColor))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val mWidth = (1.5 * MeasureSpec.getSize(widthMeasureSpec)).toInt()
        val mHeight = (1.5 * MeasureSpec.getSize(heightMeasureSpec)).toInt()

        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        if (isChecked) {
            picker.style = Paint.Style.FILL
            color?.let { picker.color = it }
            marker.style = Paint.Style.FILL
            alphaColor?.let { marker.color = it }
            canvas?.drawCircle((width/2).toFloat(), (height/2).toFloat(), (width/2).toFloat(), marker)
            canvas?.drawCircle((width/2).toFloat(), (height/2).toFloat(), (width/3).toFloat(), picker)
        } else {
            picker.style = Paint.Style.FILL
            color?.let { picker.color = it }
            canvas?.drawCircle((width/2).toFloat(), (height/2).toFloat(), (width/3).toFloat(), picker)
        }
    }
}