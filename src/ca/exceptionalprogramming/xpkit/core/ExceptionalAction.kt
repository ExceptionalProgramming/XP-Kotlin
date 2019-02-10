package ca.exceptionalprogramming.xpkit.core

typealias ExceptionalAction = () -> Nothing

fun <T> ExceptionalAction.getAs(): T {
    try {
        this.invoke()
    } catch (valueException: ValueException) {
        return valueException.getAs()
    }
}