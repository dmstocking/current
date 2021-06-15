package me.stockingd.current

inline fun <T> Array<T>.withEach(action: T.() -> Unit) {
    forEach { with(it, action) }
}
