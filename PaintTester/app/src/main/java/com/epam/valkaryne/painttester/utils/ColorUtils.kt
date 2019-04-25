@file:JvmName("ExtensionsUtils")

package com.epam.valkaryne.painttester.utils

fun getHexColor(value: Int?) : String {
    return if (value != null)
        "#${Integer.toHexString(value).toUpperCase().substring(2)}"
    else
        ""
}