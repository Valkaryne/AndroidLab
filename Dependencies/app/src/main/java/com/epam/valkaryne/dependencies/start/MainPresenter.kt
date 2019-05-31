package com.epam.valkaryne.dependencies.start

import timber.log.Timber

class MainPresenter(private val order: Order) {

    fun makeCoffee() {
        Timber.i(order.execute())
    }
}