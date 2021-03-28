package com.benmohammad.mvi2.mvi

interface Intent<T> {

    fun reduce(oldState: T): T
}

fun <T> intent(block: T.() -> T): Intent<T> {
    return object : Intent<T> {
        override fun reduce(oldState: T): T {
            return block(oldState)
        }
    }
}

fun <T> sideEffect(block: T.() -> Unit): Intent<T> {
    return intent {
        block()
        this
    }
}