package ca.exceptionalprogramming.xpkit.sample

import ca.exceptionalprogramming.xpkit.arithmetic.plus
import ca.exceptionalprogramming.xpkit.core.ValueException
import ca.exceptionalprogramming.xpkit.core.getAs
import ca.exceptionalprogramming.xpkit.core.throwResult

fun main() {
    print({ fib(10) }.getAs<Int>())
}

fun fib(n: Int): Nothing {
    when (n) {
        0, 1 -> throw ValueException(1)
        else -> {
            ({ fib(n - 1) } + { fib(n - 2) }).throwResult()
        }
    }
}