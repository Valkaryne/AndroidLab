package com.epam.valkaryne.rxsonglist.viewmodel

import androidx.lifecycle.ViewModel
import com.epam.valkaryne.rxsonglist.adapter.SongsAdapter
import com.epam.valkaryne.rxsonglist.model.Repository
import com.epam.valkaryne.rxsonglist.model.Song
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.ReplaySubject

/**
 * This [ViewModel] is responsible for getting songs data from repository and putting it to the adapter of
 * the recycler view.
 *
 * @author Valentine Litvin
 */

class SongsViewModel : ViewModel() {

    val adapter: SongsAdapter = SongsAdapter()
    var query = ""
        set(value) {
            field = value.toLowerCase()
        }

    private var disposable: Disposable? = null
    private val replaySubject = ReplaySubject.create<Song>()

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    fun initialize() {
        fetchSongsFromRepository()
    }

    fun filterSongsInAdapter() {
        adapter.clearItems()
        disposable?.dispose()
        disposable = replaySubject
            .filter { song -> song.title.toLowerCase().contains(query) }
            .subscribeBy(
                onNext = { song -> adapter.addItem(song) }
            )
    }

    private fun fetchSongsFromRepository() {
        Repository().getItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(replaySubject)
        filterSongsInAdapter()
    }
}