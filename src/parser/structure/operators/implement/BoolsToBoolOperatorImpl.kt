package parser.structure.operators.implement

import parser.structure.operators.BoolsToBoolOperator

enum class BoolsToBoolOperatorImpl : BoolsToBoolOperator {
    AND {
        override fun apply(a: Boolean, b: Boolean): Boolean = a && b
        override fun toString(): String = "&"
    },
    OR {
        override fun apply(a: Boolean, b: Boolean): Boolean = a || b
        override fun toString(): String = "|"
    };
}