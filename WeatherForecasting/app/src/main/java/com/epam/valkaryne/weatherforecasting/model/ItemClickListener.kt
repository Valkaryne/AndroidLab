package com.epam.valkaryne.weatherforecasting.model

interface ItemClickListener {
    fun onItemClick(position: Int)

    fun onItemLongClick(position: Int, addItem: Boolean) : Boolean
}