package array.expression.structure.expression

import array.expression.simplify.stucture.Polinomial
import java.math.BigInteger

interface Expression<T> {
    fun evaluate(element: BigInteger) : T
    fun clone(): Expression<T>
    override fun toString(): String
    fun toPolinomial(): Polinomial
}
