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
import parser.structure.operators.implement.NumbersToBoolOperatorImpl
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

    private val easyExamples: List<Pair<CallChain, String>> = listOf(
            Pair(CallChainImpl(Filter(BinaryExpression(NumbersToBoolOperatorImpl.LESS_THAN, Element(), Element())), CallChainImpl(Mapper(Constant(BigInteger("-6641849157192254023"))), Mapper(BinaryExpression(NumbersToNumberOperatorImpl.MULTIPLY, Constant(BigInteger("-3711132188216687009")), Element())))),
                    "filter{(element<element)}%>%map{-6641849157192254023}%>%map{(-3711132188216687009*element)}"),
            Pair(CallChainImpl(Filter(BinaryExpression(NumbersToBoolOperatorImpl.MORE_THAN, Constant(BigInteger("605613220738032425")), Element())), CallChainImpl(Filter(BinaryExpression(NumbersToBoolOperatorImpl.EQUALS, Element(), Constant(BigInteger("-3104336864636073749")))), Mapper(Constant(BigInteger("-9164101171279437131"))))),
                    "filter{(605613220738032425>element)}%>%filter{(element=-3104336864636073749)}%>%map{-9164101171279437131}"),
            Pair(CallChainImpl(Mapper(Constant(BigInteger("2619924829012818875"))), CallChainImpl(Filter(BinaryExpression(NumbersToBoolOperatorImpl.EQUALS, Element(), Constant(BigInteger("8211661635828948923")))), Mapper(Constant(BigInteger("4362819833674321318"))))),
                    "map{2619924829012818875}%>%filter{(element=8211661635828948923)}%>%map{4362819833674321318}"),
            Pair(CallChainImpl(Mapper(Constant(BigInteger("4946555178474214325"))), CallChainImpl(Filter(BinaryExpression(NumbersToBoolOperatorImpl.EQUALS, Constant(BigInteger("3299500085372830107")), Element())), Filter(BinaryExpression(NumbersToBoolOperatorImpl.EQUALS, Element(), Element())))),
                    "map{4946555178474214325}%>%filter{(3299500085372830107=element)}%>%filter{(element=element)}"),
            Pair(CallChainImpl(Mapper(BinaryExpression(NumbersToNumberOperatorImpl.SUBTRACT, Element(), Element())), CallChainImpl(Mapper(BinaryExpression(NumbersToNumberOperatorImpl.SUBTRACT, Element(), Constant(BigInteger("-605272835938492183")))), Mapper(Constant(BigInteger("-7950753862706049061"))))),
                    "map{(element-element)}%>%map{(element--605272835938492183)}%>%map{-7950753862706049061}"),
            Pair(CallChainImpl(Filter(BinaryExpression(NumbersToBoolOperatorImpl.EQUALS, Element(), Element())), CallChainImpl(Filter(BinaryExpression(NumbersToBoolOperatorImpl.LESS_THAN, Element(), Element())), Filter(BinaryExpression(NumbersToBoolOperatorImpl.MORE_THAN, Constant(BigInteger("4595752336043432313")), Element())))),
                    "filter{(element=element)}%>%filter{(element<element)}%>%filter{(4595752336043432313>element)}"),
            Pair(CallChainImpl(Filter(BinaryExpression(NumbersToBoolOperatorImpl.LESS_THAN, Element(), Constant(BigInteger("-1927695177376177922")))), CallChainImpl(Filter(BinaryExpression(NumbersToBoolOperatorImpl.EQUALS, Element(), Constant(BigInteger("6899403544001071481")))), Mapper(BinaryExpression(NumbersToNumberOperatorImpl.MULTIPLY, Element(), Element())))),
                    "filter{(element<-1927695177376177922)}%>%filter{(element=6899403544001071481)}%>%map{(element*element)}"),
            Pair(CallChainImpl(Mapper(Element()), CallChainImpl(Mapper(Constant(BigInteger("6677818175895881385"))), Mapper(Element()))),
                    "map{element}%>%map{6677818175895881385}%>%map{element}"),
            Pair(CallChainImpl(Mapper(BinaryExpression(NumbersToNumberOperatorImpl.MULTIPLY, Element(), Constant(BigInteger("-1748975985928300240")))), CallChainImpl(Filter(BinaryExpression(NumbersToBoolOperatorImpl.EQUALS, Constant(BigInteger("-5379759707642999802")), Constant(BigInteger("352665512903617752")))), Filter(BinaryExpression(NumbersToBoolOperatorImpl.LESS_THAN, Element(), Constant(BigInteger("-1314583102241227267")))))),
                    "map{(element*-1748975985928300240)}%>%filter{(-5379759707642999802=352665512903617752)}%>%filter{(element<-1314583102241227267)}"),
            Pair(CallChainImpl(Mapper(BinaryExpression(NumbersToNumberOperatorImpl.MULTIPLY, Element(), Element())), CallChainImpl(Filter(BinaryExpression(NumbersToBoolOperatorImpl.LESS_THAN, Constant(BigInteger("-6543022210216003672")), Element())), Mapper(BinaryExpression(NumbersToNumberOperatorImpl.PLUS, Constant(BigInteger("4568336594332980021")), Element())))),
                    "map{(element*element)}%>%filter{(-6543022210216003672<element)}%>%map{(4568336594332980021+element)}"),
            Pair(CallChainImpl(Filter(BinaryExpression(NumbersToBoolOperatorImpl.EQUALS, Constant(BigInteger("-119717673998280558")), Constant(BigInteger("2844545924498587101")))), CallChainImpl(Mapper(Constant(BigInteger("-5607962724919381360"))), Mapper(BinaryExpression(NumbersToNumberOperatorImpl.MULTIPLY, Constant(BigInteger("4674727950018836139")), Constant(BigInteger("3476213062587117830")))))),
                    "filter{(-119717673998280558=2844545924498587101)}%>%map{-5607962724919381360}%>%map{(4674727950018836139*3476213062587117830)}")
    )
    @Test
    fun testEasyRandom() {
        for (pair in easyExamples) {
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