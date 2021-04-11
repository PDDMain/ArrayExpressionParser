package array.expression.simplify.stucture

import array.expression.structure.expression.BinaryExpression
import array.expression.structure.expression.Constant
import array.expression.structure.expression.Element
import array.expression.structure.expression.Expression
import array.expression.structure.operators.implement.NumbersToNumberOperatorImpl
import java.lang.IllegalArgumentException
import java.math.BigInteger

class Argument(val k: BigInteger, val pow: Int) {
    fun toExpression(): Expression<BigInteger> {
        if (pow == 0 || k == BigInteger.ZERO) {
            return Constant(k)
        }
        var expr: Expression<BigInteger> = Element()
        for (i in 1 until pow) {
            expr = BinaryExpression(NumbersToNumberOperatorImpl.MULTIPLY, Element(), expr)
        }
        return BinaryExpression(NumbersToNumberOperatorImpl.MULTIPLY, Constant(k), expr)
    }

    fun add(polinomial: Argument): Argument {
        if (pow != polinomial.pow) {
            throw IllegalArgumentException()
        }
        return Argument(k + polinomial.k, pow)
    }

    fun subtract(polinomial: Argument): Argument {
        if (pow != polinomial.pow) {
            throw IllegalArgumentException()
        }
        return add(polinomial.negate())
    }

    fun multiply(polinomial: Argument): Argument {
        return Argument(k * polinomial.k, pow + polinomial.pow)
    }

    fun negate(): Argument {
        return Argument(-k, pow)
    }
}