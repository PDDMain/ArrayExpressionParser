package array.expression

import array.expression.parser.ArrayParser
import array.expression.structure.callchain.call.Filter
import array.expression.structure.callchain.call.Mapper
import array.expression.structure.expression.BinaryExpression
import array.expression.structure.expression.Constant
import array.expression.structure.expression.Element
import array.expression.structure.operators.implement.NumbersToBoolOperatorImpl
import array.expression.structure.operators.implement.NumbersToNumberOperatorImpl
import org.junit.Test
import java.math.BigInteger
import kotlin.test.assertEquals

class SimplifyTest {
    @Test
    fun test00() {
        assertEquals(
                Mapper(BinaryExpression(
                        NumbersToNumberOperatorImpl.PLUS,
                        Constant(BigInteger.valueOf(5)),
                        Constant(BigInteger.valueOf(3)))).toFilterMapper().simplify().mapper.toString(),
                Mapper(Constant(BigInteger.valueOf(8))).toString()
        )
    }

    @Test
    fun test01() {
        assertEquals(
                Mapper(BinaryExpression(
                        NumbersToNumberOperatorImpl.PLUS,
                        Element(),
                        Element())).toFilterMapper().simplify().mapper.toString(),
                Mapper(BinaryExpression(
                        NumbersToNumberOperatorImpl.MULTIPLY,
                        Constant(BigInteger.valueOf(2)),
                        Element())).toString()
        )
    }

    @Test
    fun test02() {
        assertEquals(
                Mapper(BinaryExpression(
                        NumbersToNumberOperatorImpl.SUBTRACT,
                        Element(),
                        Element())).toFilterMapper().simplify().mapper.toString(),
                Mapper(Constant(BigInteger.valueOf(0))).toString()
        )
    }

    @Test
    fun test03() {
        assertEquals(
                Filter(BinaryExpression(
                        NumbersToBoolOperatorImpl.LESS_THAN,
                        BinaryExpression(
                                NumbersToNumberOperatorImpl.MULTIPLY,
                                Element(),
                                Element()),
                        BinaryExpression(
                                NumbersToNumberOperatorImpl.MULTIPLY,
                                Element(),
                                Element()))).toFilterMapper().simplify().filter.toString(),
                Filter(BinaryExpression(
                        NumbersToBoolOperatorImpl.EQUALS,
                        Constant(BigInteger.ZERO),
                        Constant(BigInteger.ONE))).toString()
        )
    }
}