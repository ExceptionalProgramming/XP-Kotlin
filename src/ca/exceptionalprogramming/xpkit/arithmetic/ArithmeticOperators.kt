package ca.exceptionalprogramming.xpkit.arithmetic

import ca.exceptionalprogramming.xpkit.core.ExceptionalAction
import ca.exceptionalprogramming.xpkit.core.ValueException
import ca.exceptionalprogramming.xpkit.core.throwResult

operator fun ExceptionalAction.plus(other: ExceptionalAction) = { executeOperation<Int>(this, other, Int::plus) }
operator fun ExceptionalAction.minus(other: ExceptionalAction) = { executeOperation<Int>(this, other, Int::minus) }
operator fun ExceptionalAction.times(other: ExceptionalAction) = { executeOperation<Int>(this, other, Int::times) }
operator fun ExceptionalAction.div(other: ExceptionalAction) = { executeOperation<Int>(this, other, Int::div) }

fun <T : Any> executeOperation(lhs: ExceptionalAction, rhs: ExceptionalAction, operation: (T, T) -> T): Nothing {
    val lhsResult = try {
        lhs.throwResult()
    } catch (valueException: ValueException) {
        valueException.getAs<T>()
    }

    val rhsResult = try {
        rhs.throwResult()
    } catch (valueException: ValueException) {
        valueException.getAs<T>()
    }

    throw ValueException(operation(lhsResult, rhsResult))
}

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