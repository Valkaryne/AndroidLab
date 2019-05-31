package com.epam.valkaryne.dependencies.start

import java.util.*

const val espresso = "Espresso"
const val americano = "Americano"
const val cappucino = "Cappucino"
const val latte = "Latte"
const val frappe = "Frappe"
const val irish = "Irish Coffee"

class CapOfCoffee {
    private val dayOfTheWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    val coffeeOfTheDay = when (dayOfTheWeek) {
        Calendar.MONDAY -> espresso
        Calendar.TUESDAY -> americano
        Calendar.WEDNESDAY -> cappucino
        Calendar.THURSDAY -> latte
        Calendar.FRIDAY -> frappe
        else -> irish
    }
}