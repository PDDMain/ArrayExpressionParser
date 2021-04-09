package array.expression

import array.expression.structure.callchain.CallChainImpl
import array.expression.structure.callchain.call.Filter
import array.expression.structure.callchain.call.Mapper
import array.expression.structure.expression.BinaryExpression
import array.expression.structure.expression.Constant
import array.expression.structure.expression.Element
import array.expression.structure.operators.implement.BoolsToBoolOperatorImpl
import array.expression.structure.operators.implement.NumbersToBoolOperatorImpl
import java.math.BigInteger

fun main() {
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
    println(callChain.toString())
    println(callChain.toFilterMapper().toString())
}