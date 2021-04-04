package parser.structure.operators.implement

import parser.structure.operators.Operator
import java.math.BigInteger

enum class NumbersToBoolsOperatorImpl : Operator<BigInteger, Boolean> {
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