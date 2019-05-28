package com.epam.valkaryne.rxsonglist.model

import com.epam.valkaryne.rxsonglist.R
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * The inner [Repository] provides the model of the application with required data and has only one
 * method [getItems] returns set of songs data one-by-one.
 *
 * @author Valentine Litvin
 */

class Repository {

    private val titles = arrayListOf(
        "The Night King",
        "Shattered",
        "Khazad Dum",
        "Once My Light",
        "Stellar Tombs",
        "The Blood of Ardennes",
        "Through the Mists of Oblivion"
    )

    private val artists = arrayListOf(
        "Ramin Djawadi",
        "Delain",
        "Summoning",
        "Sirenia",
        "Draconian",
        "Unshine",
        "Atargatis"
    )

    private val lengths = arrayListOf(
        "8:50",
        "4:19",
        "10:57",
        "7:20",
        "6:02",
        "4:35",
        "6:39"
    )

    private val imgResources = arrayListOf(
        R.drawable.got,
        R.drawable.delain,
        R.drawable.summoning,
        R.drawable.sirenia,
        R.drawable.draconian,
        R.drawable.unshine,
        R.drawable.atargatis
    )

    fun getItems(): Observable<Song> =
        Observable
            .range(0, 7)
            .concatMap { i ->
                Observable.just(
                    Song(titles[i], artists[i], lengths[i], imgResources[i])
                ).delay(5, TimeUnit.SECONDS)
            }
}