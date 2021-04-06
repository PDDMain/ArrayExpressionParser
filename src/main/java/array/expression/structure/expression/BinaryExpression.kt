package array.expression.structure.expression

import array.expression.structure.operators.Operator
import java.math.BigInteger

class BinaryExpression<T, R>(val operator: Operator<T, R>, val left: Expression<T>, val right: Expression<T>,) : Expression<R> {
    override fun evaluate(element: BigInteger): R {
        return operator.apply(left.evaluate(element), right.evaluate(element))
    }

    override fun toString(): String {
        return "($left$operator$right)"
    }

    override fun clone(): Expression<R> {
        return BinaryExpression(operator, left.clone(), right.clone())
    }
}