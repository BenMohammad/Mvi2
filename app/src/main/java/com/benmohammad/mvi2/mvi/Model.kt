package com.benmohammad.mvi2.mvi

import io.reactivex.Observable

interface Model<S> {
    fun process(intent: Intent<S>)
    fun modelState(): Observable<S>
}