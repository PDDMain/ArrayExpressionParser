import com.sun.tools.javac.Main
import parser.structure.callchain.CallChainImpl
import parser.structure.callchain.call.Filter
import parser.structure.callchain.call.Mapper
import parser.structure.expression.BinaryExpression
import parser.structure.expression.Constant
import parser.structure.expression.Element
import parser.structure.operators.implement.BoolsToBoolOperatorImpl
import parser.structure.operators.implement.NumbersToBoolOperatorImpl
import parser.structure.operators.implement.NumbersToNumberOperatorImpl
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