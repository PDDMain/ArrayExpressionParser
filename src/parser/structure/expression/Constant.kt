package parser.structure.expression

import parser.structure.exception.TypeException

class Constant(private val value: Int): Expression {
    override fun evaluateBool(element: Int): Boolean {
        throw TypeException()
    }

    override fun evaluateInt(element: Int): Int {
        return value
    }

    override fun toString(): String {
        return value.toString()
    }
}