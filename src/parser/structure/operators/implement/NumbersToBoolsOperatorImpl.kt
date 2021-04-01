package parser.structure.operators.implement

import parser.structure.operators.NumbersToBoolOperator

enum class NumbersToBoolsOperatorImpl : NumbersToBoolOperator {
    EQUALS {
        override fun apply(a: Int, b: Int): Boolean = a == b
        override fun toString(): String = "="
    },
    MORE_THAN {
        override fun apply(a: Int, b: Int): Boolean = a > b
        override fun toString(): String = ">"
    },
    LESS_THAN {
        override fun apply(a: Int, b: Int): Boolean = a < b
        override fun toString(): String = "<"
    };
}