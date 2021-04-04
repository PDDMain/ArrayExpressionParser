package parser.structure.expression

import parser.structure.operators.Operator
import java.math.BigInteger

class BinaryExpression<T, R>(private val operator: Operator<T, R>, private val left: Expression<T>, private val right: Expression<T>,) : Expression<R> {
    override fun evaluate(element: BigInteger): R {
        return operator.apply(left.evaluate(element), right.evaluate(element))
    }

    override fun toString(): String {
        return "($left$operator$right)"
    }
}