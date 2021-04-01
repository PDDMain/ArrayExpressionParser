package parser.structure.expression

import parser.structure.exception.TypeException
import parser.structure.operators.BoolsToBoolOperator
import parser.structure.operators.NumbersToBoolOperator
import parser.structure.operators.NumbersToNumberOperator
import parser.structure.operators.Operator

class BinaryExpression(private val left: Expression, private val right: Expression, private val operator: Operator) : Expression {
    override fun evaluateBool(element: Int): Boolean {
        if (operator is NumbersToBoolOperator) {
            val numbersToBoolOperator: NumbersToBoolOperator = operator
            return numbersToBoolOperator.apply(left.evaluateInt(element), right.evaluateInt(element))
        }
        if (operator is BoolsToBoolOperator) {
            val boolsToBoolOperator: BoolsToBoolOperator = operator
            return boolsToBoolOperator.apply(left.evaluateBool(element), right.evaluateBool(element))
        }
        throw TypeException()
    }

    override fun evaluateInt(element: Int): Int {
        if (operator is NumbersToNumberOperator) {
            val numbersToNumberOperator: NumbersToNumberOperator = operator
            return numbersToNumberOperator.apply(left.evaluateInt(element), right.evaluateInt(element))
        }
        throw TypeException()
    }

    override fun toString(): String {
        return "($left$operator$right)"
    }
}