package parser.structure.expression

import parser.structure.exception.TypeException
import java.math.BigInteger

class Constant(private val value: BigInteger): Expression<BigInteger> {

    override fun evaluate(element: BigInteger): BigInteger {
        return value
    }

    override fun toString(): String {
        return value.toString()
    }
}