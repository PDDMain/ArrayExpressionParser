package parser.structure.operators.implement

import parser.structure.exception.DivideException
import parser.structure.operators.NumbersToNumberOperator

enum class NumbersToNumberOperatorImpl : NumbersToNumberOperator {
    PLUS {
        override fun apply(a: Int, b: Int): Int = a + b
        override fun toString(): String = "+"
    },
    SUBTRACT {
        override fun apply(a: Int, b: Int): Int = a - b
        override fun toString(): String = "-"
    },
    MULTIPLY {
        override fun apply(a: Int, b: Int): Int = a * b
        override fun toString(): String = "*"
    },
    DIVIDE {
        override fun apply(a: Int, b: Int): Int {
            if (b == 0) {
                throw DivideException("'$a/$b' cannot divide")
            }
            return a / b
        }
        override fun toString(): String = "/"
    };
}