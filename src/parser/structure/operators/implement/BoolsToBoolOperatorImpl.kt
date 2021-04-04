package parser.structure.operators.implement

import parser.structure.operators.Operator

enum class BoolsToBoolOperatorImpl : Operator<Boolean, Boolean> {
    AND {
        override fun apply(a: Boolean, b: Boolean): Boolean = a && b
        override fun toString(): String = "&"
    },
    OR {
        override fun apply(a: Boolean, b: Boolean): Boolean = a || b
        override fun toString(): String = "|"
    };
}