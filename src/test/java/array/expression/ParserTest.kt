package array.expression

import array.expression.examples.EasyExamples
import array.expression.examples.Examples
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import array.expression.parser.ArrayParser
import array.expression.parser.exception.NoEndParserException
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
import java.lang.StringBuilder
import java.math.BigInteger
import kotlin.test.assertEquals

class ParserTest {
    @Test
    fun test00() {
        assertEquals(
                Mapper(BinaryExpression(
                        NumbersToNumberOperatorImpl.PLUS,
                        Element(),
                        Constant(BigInteger.valueOf(3)))).toString(),
                ArrayParser().parse("map{(element+3)}").toString()
        )
    }

    @Test
    fun test01() {
        assertEquals(
                ArrayParser().parse("filter{((element>10)&(element<20))}%>%map{element}").toString(),
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
    fun test02() {
        assertEquals(ArrayParser().parse("map{(element+10)}%>%filter{(element>10)}%>%map{(element*element)}").toString(),
                CallChainImpl(
                        Mapper(BinaryExpression(
                                NumbersToNumberOperatorImpl.PLUS,
                                Element(),
                                Constant(BigInteger.valueOf(10))
                        )),
                        CallChainImpl(
                                Filter(BinaryExpression(
                                        NumbersToBoolOperatorImpl.MORE_THAN,
                                        Element(),
                                        Constant(BigInteger.valueOf(10))
                                )),
                                Mapper(BinaryExpression(
                                        NumbersToNumberOperatorImpl.MULTIPLY,
                                        Element(),
                                        Element()
                                ))
                        )
                ).toString()
        )
    }

    @Test
    fun test03() {
        assertEquals(ArrayParser().parse("filter{(element>0)}%>%filter{(element<0)}%>%map{(element*element)}").toString(),
                CallChainImpl(
                        Filter(BinaryExpression(
                                NumbersToBoolOperatorImpl.MORE_THAN,
                                Element(),
                                Constant(BigInteger.valueOf(0))
                        )),
                        CallChainImpl(
                                Filter(BinaryExpression(
                                        NumbersToBoolOperatorImpl.LESS_THAN,
                                        Element(),
                                        Constant(BigInteger.valueOf(0))
                                )),
                                Mapper(BinaryExpression(
                                        NumbersToNumberOperatorImpl.MULTIPLY,
                                        Element(),
                                        Element()
                                ))
                        )
                ).toString()
        )
    }

    @Test
    fun testManyMapPlus() {
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
        assertEquals(ArrayParser().parse(expected.toString()).toString(),
                Mapper(expression).toString())
    }

    @Test
    fun testNoEnd() {
        assertThrows<NoEndParserException> {
            ArrayParser().parse("filter{(element>0)}%>%filter{(element<0)}%>%map{(element*element)} ")
        }
    }

    @Test
    fun testNumber() {
        val number = 10L
        assertEquals(ArrayParser().parse("map{$number}").toString(), Mapper(Constant(BigInteger.valueOf(number))).toString())
    }


    @Test
    fun testLongNumber() {
        val number = BigInteger("9876543238798745983762482631596213985713987258437586342095938645862319865862310956923785091364509712")
        assertEquals(ArrayParser().parse("map{$number}").toString(), Mapper(Constant(number)).toString())
    }

    @Test
    fun testEasyRandom() {
        for (pair in EasyExamples().easyExamples) {
            assertEquals(ArrayParser().parse(pair.second).toString(), pair.first.toString())
        }
    }

    @Test
    fun testHardRandom() {
        for (pair in Examples().examples) {
            assertEquals(ArrayParser().parse(pair.second).toString(), pair.first.toString())
        }
    }
}