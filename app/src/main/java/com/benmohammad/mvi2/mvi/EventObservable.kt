package com.benmohammad.mvi2.mvi

import io.reactivex.Observable

interface EventObservable<E> {
    fun events(): Observable<E>
}