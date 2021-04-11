package array.expression.structure.operators.implement

import array.expression.structure.operators.Operator
import java.math.BigInteger

enum class NumbersToBoolOperatorImpl : Operator<BigInteger, Boolean> {
    EQUALS {
        override fun apply(a: BigInteger, b: BigInteger): Boolean = a == b
        override fun toString(): String = "="
        override fun isImplicated(): Boolean = true
    },
    MORE_THAN {
        override fun apply(a: BigInteger, b: BigInteger): Boolean = a > b
        override fun toString(): String = ">"
        override fun isImplicated(): Boolean = false
    },
    LESS_THAN {
        override fun apply(a: BigInteger, b: BigInteger): Boolean = a < b
        override fun toString(): String = "<"
        override fun isImplicated(): Boolean = false
    };
}