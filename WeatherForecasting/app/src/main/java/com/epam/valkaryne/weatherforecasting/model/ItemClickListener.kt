package com.epam.valkaryne.weatherforecasting.model

/**
 * Interface provides handling of the RecyclerView items by single click and long click
 *
 * @author Valentine Litvin
 */
interface ItemClickListener {
    fun onItemClick(position: Int)

    fun onItemLongClick(position: Int, addItem: Boolean) : Boolean
}