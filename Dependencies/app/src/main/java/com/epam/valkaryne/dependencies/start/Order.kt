package com.epam.valkaryne.dependencies.start

class Order {

    private lateinit var coffee: CapOfCoffee

    constructor() {
        coffee = CapOfCoffee()
    }

    fun execute(): String = "[ORDER] Your ${coffee.coffeeOfTheDay} is ready!"
}