package ca.exceptionalprogramming.xpkit.core

typealias ExceptionalAction = () -> Nothing

fun ExceptionalAction.throwResult(): Nothing {
    this.invoke()
}

fun <T> ExceptionalAction.getAs(): T {
    try {
        this.invoke()
    } catch (valueException: ValueException) {
        return valueException.getAs()
    }
}