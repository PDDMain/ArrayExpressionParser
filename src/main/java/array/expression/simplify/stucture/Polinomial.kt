package array.expression.simplify.stucture

import array.expression.structure.expression.BinaryExpression
import array.expression.structure.expression.Constant
import array.expression.structure.expression.Expression
import array.expression.structure.operators.implement.NumbersToNumberOperatorImpl
import java.math.BigInteger
import kotlin.math.max

class Polinomial(val arguments: Map<Int, Argument>) {
    fun toExpression(): Expression<BigInteger> {
        if (arguments.isEmpty()) {
            return Constant(BigInteger.valueOf(0))
        }
        var expr: Expression<BigInteger> = Constant(BigInteger.ZERO)
        for (arg in arguments.values) {
            if (expr == Constant(BigInteger.ZERO)) {
                expr = arg.toExpression()
            }
            if (arg.k != BigInteger.ZERO) {
                expr = BinaryExpression(NumbersToNumberOperatorImpl.PLUS, expr, arg.toExpression())
            }
        }
        return expr
    }

    fun add(polinomial: Polinomial): Polinomial {
        val map: MutableMap<Int, Argument> = arguments.toMutableMap()
        for (pair in map.entries) {
            if (polinomial.arguments.containsKey(pair.key)) {
                map[pair.key] = map[pair.key]!!.add(polinomial.arguments[pair.key]!!)
            }
        }
        for (pair in polinomial.arguments.entries) {
            if (!map.containsKey(pair.key)) {
                map[pair.key] = pair.value
            }
        }
        return Polinomial(map)
    }

    fun subtract(polinomial: Polinomial): Polinomial {
        val map: MutableMap<Int, Argument> = arguments.toMutableMap()
        for (pair in map.entries) {
            if (polinomial.arguments.containsKey(pair.key)) {
                map[pair.key] = map[pair.key]!!.add(polinomial.arguments[pair.key]!!.negate())
            }
        }
        for (pair in polinomial.arguments.entries) {
            if (!map.containsKey(pair.key)) {
                map[pair.key] = pair.value.negate()
            }
        }
        return Polinomial(map)
    }

    fun multiply(polinomial: Polinomial): Polinomial {
        val map = emptyMap<Int, Argument>().toMutableMap()
        for (a1 in arguments.entries) {
            for (a2 in polinomial.arguments.entries) {
                val pow = a1.key + a2.key
                if (map.containsKey(pow)) {
                    map[pow] = map[pow]!!.add(a1.value.multiply(a2.value))
                } else {
                    map[pow] = a1.value.multiply(a2.value)
                }
            }
        }
        return Polinomial(map)
    }

    fun maxPow(): Int {
        var max = 0
        for (arg in arguments.values) {
            max = if (arg.k != BigInteger.ZERO) max(max, arg.pow) else max
        }
        return max
    }

    fun elementsCount(): BigInteger {
        var sum = BigInteger.ZERO
        for (pow in arguments.keys) {
            sum += BigInteger.valueOf(pow.toLong())
        }
        return sum
    }
}