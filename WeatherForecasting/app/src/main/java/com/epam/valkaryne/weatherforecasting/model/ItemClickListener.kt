package com.epam.valkaryne.weatherforecasting.model

interface ItemClickListener {
    fun onItemClick(position: Int, addItem: Boolean)

    fun onItemLongClick(position: Int, addItem: Boolean) : Boolean
}