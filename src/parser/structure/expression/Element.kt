package parser.structure.expression

import parser.structure.exception.TypeException

class Element: Expression {
    override fun evaluateBool(element: Int): Boolean {
        throw TypeException()
    }

    override fun evaluateInt(element: Int): Int {
        return element
    }

    override fun toString(): String {
        return "element"
    }
}