package array.expression.structure.expression

import array.expression.simplify.stucture.Argument
import array.expression.simplify.stucture.Polinomial
import java.math.BigInteger

class Constant(val value: BigInteger) : Expression<BigInteger> {

    override fun evaluate(element: BigInteger): BigInteger {
        return value
    }

    override fun toString(): String {
        return value.toString()
    }

    override fun clone(): Expression<BigInteger> {
        return Constant(BigInteger(value.toString()))
    }

    override fun equals(other: Any?): Boolean {
        return other is Constant && value == other.value
    }

    override fun toPolinomial(): Polinomial {
        return Polinomial(mapOf(Pair(0, Argument(value, 0))))
    }
}