package ca.exceptionalprogramming.xpkit.arithmetic

import ca.exceptionalprogramming.xpkit.core.ExceptionalAction
import ca.exceptionalprogramming.xpkit.core.ValueException

operator fun ExceptionalAction.plus(other: ExceptionalAction) =
    { reduceExceptionalActions(listOf(this, other), 0, Int::plus) }

operator fun ExceptionalAction.minus(other: ExceptionalAction) =
    { reduceExceptionalActions(listOf(this, other), 0, Int::minus) }

operator fun ExceptionalAction.times(other: ExceptionalAction) =
    { reduceExceptionalActions(listOf(this, other), 0, Int::times) }

operator fun ExceptionalAction.div(other: ExceptionalAction) =
    { reduceExceptionalActions(listOf(this, other), 0, Int::div) }

fun <T : Any> reduceExceptionalActions(actions: List<ExceptionalAction>, initialValue: T, reducer: (T, T) -> T): Nothing {
    var value = initialValue
    actions.forEach { value =
            try {
                it.invoke()
            } catch (valueException: ValueException) {
                reducer(value, valueException.getAs())
            }
    }
    throw ValueException(value)
}