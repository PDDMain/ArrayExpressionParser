package array.expression.simplify

import array.expression.simplify.stucture.Polinomial
import array.expression.structure.callchain.call.Filter
import array.expression.structure.callchain.call.Mapper
import array.expression.structure.expression.BinaryExpression
import array.expression.structure.expression.Constant
import array.expression.structure.expression.Element
import array.expression.structure.expression.Expression
import array.expression.structure.operators.implement.BoolsToBoolOperatorImpl
import array.expression.structure.operators.implement.NumbersToBoolOperatorImpl
import array.expression.structure.operators.implement.NumbersToNumberOperatorImpl
import java.math.BigInteger

// simplify these types expression:
// f(const) -> const (without element)
// x - x -> ZERO
// x + x -> 2*x
// FALSE || b -> b
// TRUE && b -> b
// FALSE && b -> FALSE
// TRUE || b -> TRUE
// 1*x -> x

// cast expr to polinomial if elements in polinomial is less than in expression

@Suppress("UNCHECKED_CAST")
class Simplifier() {
    private val TRUE: Expression<Boolean> = BinaryExpression(NumbersToBoolOperatorImpl.EQUALS, Constant(BigInteger.ZERO), Constant(BigInteger.ZERO))
    private val FALSE: Expression<Boolean> = BinaryExpression(NumbersToBoolOperatorImpl.EQUALS, Constant(BigInteger.ZERO), Constant(BigInteger.ONE))

    fun simplifyMapper(mapper: Mapper): Mapper {
        if (mapper.expression is BinaryExpression<*, *>) {
            mapper.expression = simplifyNumbersToNumberExpression(mapper.expression as BinaryExpression<BigInteger, BigInteger>)
        }
        return mapper
    }

    fun simplifyFilter(filter: Filter): Filter {
        val expression: Expression<Boolean> = filter.expression
        if (expression is BinaryExpression<*, *>) {
            if (expression.operator is BoolsToBoolOperatorImpl) {
                filter.expression = simplifyBoolsToBoolExpression(filter.expression as BinaryExpression<Boolean, Boolean>)
            } else if (expression.operator is NumbersToBoolOperatorImpl) {
                filter.expression = simplifyNumbersToBoolExpression(filter.expression as BinaryExpression<BigInteger, Boolean>)
            }
        }
        return filter
    }

    private fun simplifyBoolsToBoolExpression(expression: BinaryExpression<Boolean, Boolean>): Expression<Boolean> {
        expression.left = simplifyToBoolExpression(expression.left)
        expression.right = simplifyToBoolExpression(expression.right)
        when {
            expression.left == expression.right -> {
                return expression.left
            }
            isFalse(expression.left) -> {
                return if (expression.operator == BoolsToBoolOperatorImpl.AND) {
                    FALSE
                } else {
                    expression.right
                }
            }
            isFalse(expression.right) -> {
                return if (expression.operator == BoolsToBoolOperatorImpl.AND) {
                    FALSE
                } else {
                    expression.left
                }
            }
            isTrue(expression.left) -> {
                return if (expression.operator == BoolsToBoolOperatorImpl.OR) {
                    TRUE
                } else {
                    expression.right
                }
            }
            isTrue(expression.right) -> {
                return if (expression.operator == BoolsToBoolOperatorImpl.OR) {
                    TRUE
                } else {
                    expression.left
                }
            }
            else -> {
                return expression
            }
        }
    }


    private fun isTrue(expression: Expression<Boolean>): Boolean {
        return expression == TRUE
    }

    private fun isFalse(expression: Expression<Boolean>): Boolean {
        return expression == FALSE
    }

    private fun simplifyToBoolExpression(expression: Expression<Boolean>): Expression<Boolean> {
        return if (expression is BinaryExpression<*, *>) {
            if (expression.operator is BoolsToBoolOperatorImpl) {
                simplifyBoolsToBoolExpression(expression as BinaryExpression<Boolean, Boolean>)
            } else {
                simplifyNumbersToBoolExpression(expression as BinaryExpression<BigInteger, Boolean>)
            }
        } else {
            expression
        }
    }

    private fun simplifyNumbersToBoolExpression(expression: BinaryExpression<BigInteger, Boolean>): Expression<Boolean> {
        val polinomial = expression.left.toPolinomial().subtract(expression.right.toPolinomial())
        return when {
            polinomial.maxPow() == 0 -> {
                if (polinomial.arguments.containsKey(0) && polinomial.arguments[0]?.k == BigInteger.ZERO) {
                    TRUE
                } else {
                    FALSE
                }
            }
            polinomial.maxPow() == 1 || elementsCount(expression.left) + elementsCount(expression.right) > elementsCount(polinomial.toExpression()) -> {
                BinaryExpression(expression.operator, simplifyNumbersToNumberExpression(polinomial.toExpression() as BinaryExpression<BigInteger, BigInteger>), Constant(BigInteger.ZERO))
            }
            else -> {
                if (expression.left is BinaryExpression<*, *>) {
                    expression.left = simplifyNumbersToNumberExpression(expression.left as BinaryExpression<BigInteger, BigInteger>)
                }
                if (expression.right is BinaryExpression<*, *>) {
                    expression.right = simplifyNumbersToNumberExpression(expression.right as BinaryExpression<BigInteger, BigInteger>)
                }
                expression
            }
        }
    }

    private fun elementsCount(expression: Expression<*>): BigInteger {
        return when (expression) {
            is Element -> {
                BigInteger.ONE
            }
            is BinaryExpression<*, *> -> {
                elementsCount(expression.left) + elementsCount(expression.left)
            }
            else -> {
                BigInteger.ZERO
            }
        }
    }

    private fun simplifyNumbersToNumberExpression(expression: BinaryExpression<BigInteger, BigInteger>): Expression<BigInteger> {
        if (expression.left is Constant && expression.right is Constant) {
            return Constant(expression.evaluate(BigInteger.ZERO))
        }
        if (expression.left == Constant(BigInteger.ONE) && expression.operator == NumbersToNumberOperatorImpl.MULTIPLY) {
            return expression.right
        }
        if (expression.right == Constant(BigInteger.ONE) && expression.operator == NumbersToNumberOperatorImpl.MULTIPLY) {
            return expression.left
        }
        if (expression.left is BinaryExpression<*, *>) {
            expression.left = simplifyNumbersToNumberExpression(expression.left as BinaryExpression<BigInteger, BigInteger>)
        }
        if (expression.right is BinaryExpression<*, *>) {
            expression.right = simplifyNumbersToNumberExpression(expression.right as BinaryExpression<BigInteger, BigInteger>)
        }
        if (expression.left == expression.right) {
            if (expression.operator == NumbersToNumberOperatorImpl.PLUS) {
                return BinaryExpression(NumbersToNumberOperatorImpl.MULTIPLY, Constant(BigInteger.TWO), expression.left)
            } else if (expression.operator == NumbersToNumberOperatorImpl.SUBTRACT) {
                return Constant(BigInteger.ZERO)
            }
        }
        return expression
    }
}