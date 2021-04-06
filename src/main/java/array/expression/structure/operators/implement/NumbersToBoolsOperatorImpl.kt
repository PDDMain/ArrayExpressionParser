package array.expression.structure.operators.implement

import array.expression.structure.operators.Operator
import java.math.BigInteger

enum class NumbersToBoolOperatorImpl : Operator<BigInteger, Boolean> {
    EQUALS {
        override fun apply(a: BigInteger, b: BigInteger): Boolean = a == b
        override fun toString(): String = "="
    },
    MORE_THAN {
        override fun apply(a: BigInteger, b: BigInteger): Boolean = a > b
        override fun toString(): String = ">"
    },
    LESS_THAN {
        override fun apply(a: BigInteger, b: BigInteger): Boolean = a < b
        override fun toString(): String = "<"
    };
}