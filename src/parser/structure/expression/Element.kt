package parser.structure.expression

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
}