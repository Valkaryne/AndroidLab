package com.epam.valkaryne.rxsonglist.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.epam.valkaryne.rxsonglist.adapter.SongsAdapter
import com.epam.valkaryne.rxsonglist.model.Repository
import com.epam.valkaryne.rxsonglist.model.Song
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.ReplaySubject

class SongsViewModel : ViewModel() {

    val adapter: SongsAdapter = SongsAdapter()

    private var disposable: Disposable? = null
    private val replaySubject = ReplaySubject.create<Song>()

    fun initialize() {
        Log.d("SuperCat", "Init")
        fetchSongsFromRepository()
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    private fun fetchSongsFromRepository() {
        Repository().getItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(replaySubject)
        disposable = replaySubject.subscribeBy(
            onNext = { song -> adapter.addItem(song) }
        )
    }
}