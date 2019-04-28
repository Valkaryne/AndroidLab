package com.epam.valkaryne.weatherforecasting.model

import com.epam.valkaryne.weatherforecasting.model.WeatherData.WeatherType

class CityInfo(val cityName: String, val cityInfo: String,
                    val cityImage: String, val cityDescription: String,
                    val weatherData: WeatherData
) : ForecastElement(cityName, false) {

    var isFavorite = false

    companion object {
        val cities = arrayOf(
            CityInfo(
                "Prague",
                "Czech",
                "https://tripmydream.cc/travelhub/travel/block_gallery/28/356/gallery_big_28356.jpg",
                "A magical city with an amazing architectural ensemble, which harmoniously combines medieval castles and modern buildings",
                WeatherData(WeatherType.CLOUDS, 26)
            ),
            CityInfo(
                "Vienna",
                "Austria",
                "https://tripmydream.cc/travelhub/travel/block_gallery/73/10/gallery_big_7310.jpg",
                "A city of magnificent palaces of the most ancient Austrian princely dynasties and magical cathedrals",
                WeatherData(WeatherType.SUNNY, 25)
            ),
            CityInfo(
                "Copenhagen",
                "Denmark",
                "https://tripmydream.cc/travelhub/travel/block_gallery/83/96/gallery_big_8396.jpg",
                "A cozy and hospitable city with clean air and charming narrow streets in historical areas",
                WeatherData(WeatherType.SUNNY, 19)
            ),
            CityInfo(
                "Oslo",
                "Norway",
                "https://tripmydream.cc/travelhub/travel/block_gallery/24/803/gallery_big_24803.jpg",
                "A city with amazing local architecture, the good nature of the locals, the choc sea food and, of course, the beaty of the northern nature",
                WeatherData(WeatherType.RAIN, 16)
            ),
            CityInfo(
                "Stockholm",
                "Sweden",
                "https://tripmydream.cc/travelhub/travel/block_gallery/15/554/gallery_big_15554.jpg",
                "A city of legendary band ABBA, well-known Karlsson, IKEA and the world-famous car brand Volvo",
                WeatherData(WeatherType.CLOUDS, 19)
            ),
            CityInfo(
                "Helsinki",
                "Finland",
                "https://tripmydream.cc/travelhub/travel/block_gallery/24/784/gallery_big_24784.jpg",
                "A city where statues of workers and peasants made in the beast traditions of socialist realism side by side with luxurious mansions of the 18th centruy",
                WeatherData(WeatherType.RAIN, 15)
            ),
            CityInfo(
                "Reykjavik",
                "Iceland",
                "https://tripmydream.cc/travelhub/travel/block_gallery/82/318/gallery_big_82318.jpg",
                "A city with small houses of the same type upholstered with multi-colored tin soaked in the magic of the Iceland capital",
                WeatherData(WeatherType.SNOW, 2)
            ),
            CityInfo(
                "Edinburgh",
                "Scotland",
                "https://tripmydream.cc/travelhub/travel/block_gallery/82/169/gallery_big_82169.jpg",
                "A city in the north of Great Britain, the city of ale and whiskey, the birthplace of bagpipes",
                WeatherData(WeatherType.STORM, 14)
            ),
            CityInfo(
                "Venice",
                "Italy",
                "https://tripmydream.cc/travelhub/travel/block_gallery/74/73/gallery_big_7473.jpg",
                "A city of magnificent palaces and numerous bridges connecting the islands separated by canals",
                WeatherData(WeatherType.STORM, 17)
            ),
            CityInfo(
                "Nairobi",
                "Kenya",
                "https://tripmydream.cc/travelhub/travel/block_gallery/70/922/gallery_big_70922.jpg",
                "A city of fascinating African safari and shows with ancient rituals",
                WeatherData(WeatherType.CLOUDS, 25)
            ),
            CityInfo(
                "Los-Angeles",
                "USA",
                "https://tripmydream.cc/travelhub/travel/block_gallery/76/24/gallery_big_7624.jpg",
                "A city where gorgeous beaches are side by side with ultramodern freeways, exquisite celebrity villas with skyscrapers of glass and metal",
                WeatherData(WeatherType.SUNNY, 15)
            ),
            CityInfo(
                "Singapore",
                "Singapore",
                "https://tripmydream.cc/travelhub/travel/block_gallery/77/80/gallery_big_7780.jpg",
                "A city where among the futuristic high rises there are ginger gardens and orchid parks and even a giant ship on the top of 55-story towers",
                WeatherData(WeatherType.RAIN, 26)
            ),
            CityInfo(
                "Tokyo",
                "Japan",
                "https://tripmydream.cc/travelhub/travel/block_gallery/78/47/gallery_big_7847.jpg",
                "A city where each urban area is unique and interesting, and yer absolutely not like the others",
                WeatherData(WeatherType.STORM, 13)
            )
        )
    }
}