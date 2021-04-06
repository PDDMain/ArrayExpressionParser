import org.junit.jupiter.api.Test
import parser.parser.ArrayParser
import parser.structure.callchain.CallChainImpl
import parser.structure.callchain.FilterMapper
import parser.structure.callchain.call.Filter
import parser.structure.callchain.call.Mapper
import parser.structure.expression.BinaryExpression
import parser.structure.expression.Constant
import parser.structure.expression.Element
import parser.structure.operators.implement.BoolsToBoolOperatorImpl
import parser.structure.operators.implement.NumbersToBoolOperatorImpl
import parser.structure.operators.implement.NumbersToNumberOperatorImpl
import java.math.BigInteger
import kotlin.test.assertEquals

class FilterMapperCastTest {
    @Test
    fun test01() {
        val callChain = Mapper(BinaryExpression(
                NumbersToNumberOperatorImpl.PLUS,
                Element(),
                Constant(BigInteger.valueOf(3))))
        assert(callChain
                .randomEquals(callChain.toFilterMapper()))
    }

    @Test
    fun test02() {
        val callChain = CallChainImpl(
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
                )), Mapper(Element()))
        assert(callChain
                .randomEquals(callChain.toFilterMapper()))
    }

    @Test
    fun test03() {
        val callChain = CallChainImpl(
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
        )
        assert(callChain
                .randomEquals(callChain.toFilterMapper()))
    }

    @Test
    fun testNumber() {
        val number = 10L
        val callChain = Mapper(Constant(BigInteger.valueOf(number)))
        assert(callChain
                .randomEquals(callChain.toFilterMapper()))
    }


    @Test
    fun testLongNumber() {
        val number = BigInteger("9876543238798745983762482631596213985713987258437586342095938645862319865862310956923785091364509712")
        val callChain = Mapper(Constant(number))
        assert(callChain
                .randomEquals(callChain.toFilterMapper()))
    }

    @Test
    fun testEasyRandom() {
        for (pair in EasyExamples().easyExamples) {
            assert(pair.first
                    .randomEquals(pair.first.toFilterMapper()))
        }
    }

    @Test
    fun testHardRandom() {
        for (pair in Examples().examples) {
            assert(pair.first
                    .randomEquals(pair.first.toFilterMapper()))
        }
    }
}