package array.expression.structure.expression

import array.expression.simplify.stucture.Polinomial
import array.expression.structure.operators.Operator
import array.expression.structure.operators.implement.NumbersToNumberOperatorImpl
import java.math.BigInteger

open class BinaryExpression<T, R>(val operator: Operator<T, R>, var left: Expression<T>, var right: Expression<T>) : Expression<R> {
    override fun evaluate(element: BigInteger): R {
        return operator.apply(left.evaluate(element), right.evaluate(element))
    }

    override fun toString(): String {
        return "($left$operator$right)"
    }

    override fun clone(): Expression<R> {
        return BinaryExpression(operator, left.clone(), right.clone())
    }

    override fun equals(other: Any?): Boolean {
        if (other is BinaryExpression<*, *>) {
            return operator == other.operator &&
                    ((left == other.left && right == other.right) ||
                            (operator.isImplicated() && left == other.right && left == other.right))
        }
        return false
    }

    override fun toPolinomial(): Polinomial {
        return when (operator) {
            NumbersToNumberOperatorImpl.PLUS -> {
                left.toPolinomial().add(right.toPolinomial())
            }
            NumbersToNumberOperatorImpl.SUBTRACT -> {
                left.toPolinomial().subtract(right.toPolinomial())
            }
            NumbersToNumberOperatorImpl.MULTIPLY -> {
                left.toPolinomial().multiply(right.toPolinomial())
            }
            else -> throw IllegalArgumentException()
        }
    }
}