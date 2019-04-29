package com.epam.valkaryne.weatherforecasting.model

/**
 * Class defines RecyclerView item as Section
 *
 * @author Valentine Litvin
 */
data class Section(override val name: String) : ForecastElement(name, true)