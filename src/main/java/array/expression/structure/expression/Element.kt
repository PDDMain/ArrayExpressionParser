package array.expression.structure.expression

import array.expression.simplify.stucture.Argument
import array.expression.simplify.stucture.Polinomial
import java.math.BigInteger

class Element: Expression<BigInteger> {
    override fun evaluate(element: BigInteger): BigInteger {
        return element
    }

    override fun toString(): String {
        return "element"
    }

    override fun clone(): Expression<BigInteger> {
        return Element()
    }

    override fun equals(other: Any?): Boolean {
        return other is Element
    }

    override fun toPolinomial(): Polinomial {
        return Polinomial(mapOf(Pair(1, Argument(BigInteger.ONE, 1))))
    }
}