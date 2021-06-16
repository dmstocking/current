package me.stockingd.current

internal sealed class Maybe<T>
internal class Some<T>(val value: T) : Maybe<T>()
internal class None<T> : Maybe<T>()
