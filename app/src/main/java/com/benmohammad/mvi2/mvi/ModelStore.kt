package com.benmohammad.mvi2.mvi

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber


open class ModelStore<S>(startState: S): Model<S> {
    private val intents = PublishRelay.create<Intent<S>>()

    private val store = intents
            .observeOn(AndroidSchedulers.mainThread())
            .scan(startState) {oldState, intent -> intent.reduce(oldState)}
            .replay(1)
            .apply { connect() }

    private val internalDisposable = store.subscribe(::internalLogger, ::crashHandler)

    private fun internalLogger(state: S) = Timber.i("$state")

    private fun crashHandler(e: Throwable): Unit = throw e

    override fun process(intent: Intent<S>) = intents.accept(intent)

    override fun modelState(): Observable<S> = store
}