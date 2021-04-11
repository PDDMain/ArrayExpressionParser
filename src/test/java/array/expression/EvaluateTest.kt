package array.expression

import array.expression.structure.callchain.CallChainImpl
import array.expression.structure.callchain.call.Filter
import array.expression.structure.callchain.call.Mapper
import array.expression.structure.expression.BinaryExpression
import array.expression.structure.expression.Constant
import array.expression.structure.expression.Element
import array.expression.structure.expression.Expression
import array.expression.structure.operators.implement.BoolsToBoolOperatorImpl
import array.expression.structure.operators.implement.NumbersToBoolOperatorImpl
import array.expression.structure.operators.implement.NumbersToNumberOperatorImpl
import org.junit.jupiter.api.Test
import java.lang.StringBuilder
import java.math.BigInteger
import kotlin.test.assertEquals

class EvaluateTest {
    @Test
    fun test00() {
        assertEquals(
                listOf(BigInteger.valueOf(4), BigInteger.valueOf(5), BigInteger.valueOf(6), BigInteger.valueOf(7)),
                Mapper(BinaryExpression(
                        NumbersToNumberOperatorImpl.PLUS,
                        Element(),
                        Constant(BigInteger.valueOf(3)))).evaluate(listOf(BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(4)))
        )
    }

    @Test
    fun test01() {
        assertEquals(
                listOf(BigInteger.valueOf(13)),
                Filter(BinaryExpression(
                        BoolsToBoolOperatorImpl.AND,
                        BinaryExpression(
                                NumbersToBoolOperatorImpl.MORE_THAN,
                                Element(),
                                Constant(BigInteger.valueOf(10))
                        ),
                        BinaryExpression(
                                NumbersToBoolOperatorImpl.LESS_THAN,
                                Element(),
                                Constant(BigInteger.valueOf(20))
                        )
                )).evaluate(listOf(
                        BigInteger.valueOf(1),
                        BigInteger.valueOf(2),
                        BigInteger.valueOf(13),
                        BigInteger.valueOf(4),
                        BigInteger.valueOf(1047)))
        )
    }

    @Test
    fun test02() {
        assertEquals(
                listOf(BigInteger.valueOf(21), BigInteger.valueOf(12)),
                CallChainImpl(
                        Mapper(BinaryExpression(
                                NumbersToNumberOperatorImpl.MULTIPLY,
                                Element(),
                                BinaryExpression(
                                        NumbersToNumberOperatorImpl.SUBTRACT,
                                        Element(),
                                        Constant(BigInteger.valueOf(4))))),
                        Filter(BinaryExpression(
                                BoolsToBoolOperatorImpl.AND,
                                BinaryExpression(
                                        NumbersToBoolOperatorImpl.MORE_THAN,
                                        Element(),
                                        Constant(BigInteger.valueOf(11))),
                                BinaryExpression(
                                        NumbersToBoolOperatorImpl.LESS_THAN,
                                        Element(),
                                        Constant(BigInteger.valueOf(32)))
                        ))).evaluate(listOf(
                        BigInteger.valueOf(1),
                        BigInteger.valueOf(5),
                        BigInteger.valueOf(9),
                        BigInteger.valueOf(8),
                        BigInteger.valueOf(7),
                        BigInteger.valueOf(6)))
        )
    }

    @Test
    fun testManyPlus() {
        val n = 100
        var expression: Expression<BigInteger> = Element()
        for (i in 1..n) {
            expression = BinaryExpression(
                    NumbersToNumberOperatorImpl.PLUS,
                    expression,
                    Constant(BigInteger.valueOf(5)))
        }


        assertEquals(
                listOf(
                        BigInteger.valueOf(5+5*100),
                        BigInteger.valueOf(1+5*100),
                        BigInteger.valueOf(234+5*100)),
                Mapper(expression).evaluate(listOf(
                        BigInteger.valueOf(5),
                        BigInteger.valueOf(1),
                        BigInteger.valueOf(234))))
    }
}