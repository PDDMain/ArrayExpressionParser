package array.expression

import array.expression.examples.EasyExamples
import array.expression.examples.Examples
import array.expression.structure.callchain.CallChainImpl
import array.expression.structure.callchain.call.Filter
import array.expression.structure.callchain.call.Mapper
import array.expression.structure.expression.BinaryExpression
import array.expression.structure.expression.Constant
import array.expression.structure.expression.Element
import array.expression.structure.operators.implement.BoolsToBoolOperatorImpl
import array.expression.structure.operators.implement.NumbersToBoolOperatorImpl
import array.expression.structure.operators.implement.NumbersToNumberOperatorImpl
import org.junit.Test
import java.math.BigInteger

class SimplifyCastTest {
    @Test
    fun test01() {
        val callChain = Mapper(BinaryExpression(
                NumbersToNumberOperatorImpl.PLUS,
                Element(),
                Constant(BigInteger.valueOf(3))))
        assert(callChain
                .randomEquals(callChain.toSimplifyFilterMapper()))
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
                .randomEquals(callChain.toSimplifyFilterMapper()))
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
                .randomEquals(callChain.toSimplifyFilterMapper()))
    }

    @Test
    fun testNumber() {
        val number = 10L
        val callChain = Mapper(Constant(BigInteger.valueOf(number)))
        assert(callChain
                .randomEquals(callChain.toSimplifyFilterMapper()))
    }


    @Test
    fun testLongNumber() {
        val number = BigInteger("9876543238798745983762482631596213985713987258437586342095938645862319865862310956923785091364509712")
        val callChain = Mapper(Constant(number))
        assert(callChain
                .randomEquals(callChain.toSimplifyFilterMapper()))
    }

    @Test
    fun testEasyRandom() {
        for (pair in EasyExamples().easyExamples) {
            assert(pair.first
                    .randomEquals(pair.first.toSimplifyFilterMapper()))
        }
    }

//    @Test
//    fun testHardRandom() {
//        for (pair in Examples().examples) {
//            assert(pair.first
//                    .randomEquals(pair.first.toSimplifyFilterMapper()))
//        }
//    }
}
