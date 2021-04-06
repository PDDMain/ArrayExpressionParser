package array.expression

import org.junit.jupiter.api.Test
import array.expression.structure.callchain.CallChainImpl
import array.expression.structure.callchain.call.Filter
import array.expression.structure.callchain.call.Mapper
import array.expression.structure.expression.Constant
import array.expression.structure.expression.Element
import array.expression.structure.expression.Expression
import array.expression.structure.operators.implement.BoolsToBoolOperatorImpl
import array.expression.structure.operators.implement.NumbersToBoolOperatorImpl
import array.expression.structure.operators.implement.NumbersToNumberOperatorImpl
import java.lang.StringBuilder
import java.math.BigInteger
import kotlin.test.assertEquals
import array.expression.structure.expression.BinaryExpression as BinaryExpression

class StructureTest {
    @Test
    fun test00() {
        assertEquals(
                "map{(element+3)}",
                Mapper(BinaryExpression(
                        NumbersToNumberOperatorImpl.PLUS,
                        Element(),
                        Constant(BigInteger.valueOf(3)))).toString()
        )
    }

    @Test
    fun test01() {
        assertEquals(
                "filter{((element>10)&(element<20))}%>%map{element}",
                CallChainImpl(
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
                        )), Mapper(Element())).toString()
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

        val expected = StringBuilder()
        expected.append("map{")
        for (i in 1..n) {
            expected.append("(")
        }
        expected.append("element")
        for (i in 1..n) {
            expected.append("+5)")
        }
        expected.append("}")
        assertEquals(expected.toString(),
                Mapper(expression).toString())
    }
}