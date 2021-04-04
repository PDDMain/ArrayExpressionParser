import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import parser.parser.ArrayParser
import parser.parser.exception.NoEndParserException
import parser.structure.CallChain
import parser.structure.callchain.CallChainImpl
import parser.structure.callchain.call.Filter
import parser.structure.callchain.call.Mapper
import parser.structure.expression.BinaryExpression
import parser.structure.expression.Constant
import parser.structure.expression.Element
import parser.structure.expression.Expression
import parser.structure.operators.implement.BoolsToBoolOperatorImpl
import parser.structure.operators.implement.NumbersToBoolsOperatorImpl
import parser.structure.operators.implement.NumbersToNumberOperatorImpl
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
                                        NumbersToBoolsOperatorImpl.MORE_THAN,
                                        Element(),
                                        Constant(BigInteger.valueOf(10))
                                ),
                                BinaryExpression(
                                        NumbersToBoolsOperatorImpl.LESS_THAN,
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
                                        NumbersToBoolsOperatorImpl.MORE_THAN,
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
                                NumbersToBoolsOperatorImpl.MORE_THAN,
                                Element(),
                                Constant(BigInteger.valueOf(0))
                        )),
                        CallChainImpl(
                                Filter(BinaryExpression(
                                        NumbersToBoolsOperatorImpl.LESS_THAN,
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
}