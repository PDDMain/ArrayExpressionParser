import org.junit.jupiter.api.Test
import parser.structure.CallChain
import parser.structure.callchain.CallChainImpl
import parser.structure.callchain.call.Filter
import parser.structure.callchain.call.Mapper
import parser.structure.expression.Constant
import parser.structure.expression.Element
import parser.structure.expression.Expression
import parser.structure.operators.implement.BoolsToBoolOperatorImpl
import parser.structure.operators.implement.NumbersToBoolsOperatorImpl
import parser.structure.operators.implement.NumbersToNumberOperatorImpl
import java.lang.StringBuilder
import java.math.BigInteger
import kotlin.test.assertEquals
import parser.structure.expression.BinaryExpression as BinaryExpression

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