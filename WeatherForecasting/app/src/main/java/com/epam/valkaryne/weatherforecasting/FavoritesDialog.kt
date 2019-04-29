package com.epam.valkaryne.weatherforecasting

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.epam.valkaryne.weatherforecasting.model.ForecastDataModel

class FavoritesDialog : DialogFragment() {

    private var cityName: String? = null
    private var position: Int = -1
    private var addItem: Boolean = true
    private var model: ForecastDataModel? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val message = context?.let {
            if (addItem) String.format(it.getString(R.string.add_message, cityName))
            else String.format(it.getString(R.string.remove_message, cityName))
        }
        val posText = context?.let {
            if (addItem) it.getString(R.string.dialog_add)
            else it.getString(R.string.dialog_remove)
        }
        val negText = context?.getString(R.string.dialong_cancel)
        val builder = context?.let { AlertDialog.Builder(it) }
        if (message != null) {
            builder?.setMessage(message)
        }
        model?.let {
            builder?.setPositiveButton(posText) { _: DialogInterface, _: Int ->
                if (addItem) it.addFavorite(position)
                else it.removeFavorite(position)
            }
            builder?.setNegativeButton(negText) {_: DialogInterface, _: Int -> }
        }
        builder?.setCancelable(true)

        return builder?.create() as AlertDialog
    }

    companion object {
        fun newInstance(cityName: String, position: Int, addItem: Boolean, model: ForecastDataModel) : FavoritesDialog {
            val dialogFragment = FavoritesDialog()

            dialogFragment.cityName = cityName
            dialogFragment.position = position
            dialogFragment.addItem = addItem
            dialogFragment.model = model

            return dialogFragment
        }
    }
}