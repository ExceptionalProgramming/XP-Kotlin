package ca.exceptionalprogramming.xpkit.core

class ValueException(val value: Any) : Exception() {
    fun <T> getAs(): T {
        return value as T
    }
}