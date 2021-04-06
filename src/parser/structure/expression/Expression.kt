package parser.structure.expression

import java.math.BigInteger

interface Expression<T> {
    fun evaluate(element: BigInteger) : T
    fun clone(): Expression<T>
}
