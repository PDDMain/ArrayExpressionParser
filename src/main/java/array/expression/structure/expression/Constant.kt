package array.expression.structure.expression

import java.math.BigInteger

class Constant(val value: BigInteger): Expression<BigInteger> {

    override fun evaluate(element: BigInteger): BigInteger {
        return value
    }

    override fun toString(): String {
        return value.toString()
    }

    override fun clone(): Expression<BigInteger> {
        return Constant(BigInteger(value.toString()))
    }
}