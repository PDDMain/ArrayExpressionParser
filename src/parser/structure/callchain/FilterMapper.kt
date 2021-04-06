package parser.structure.callchain

import parser.structure.CallChain
import parser.structure.callchain.call.Filter
import parser.structure.callchain.call.Mapper
import parser.structure.expression.BinaryExpression
import parser.structure.expression.Constant
import parser.structure.expression.Element
import parser.structure.expression.Expression
import parser.structure.operators.implement.BoolsToBoolOperatorImpl
import parser.structure.operators.implement.NumbersToBoolOperatorImpl
import parser.structure.operators.implement.NumbersToNumberOperatorImpl
import java.math.BigInteger
import java.security.InvalidParameterException

class FilterMapper(val filter: Filter, val mapper: Mapper) : CallChain {
    override fun evaluate(arr: List<BigInteger>): List<BigInteger> {
        return mapper.evaluate(filter.evaluate(arr))
    }

    override fun toString(): String {
        return "$filter%>%$mapper"
    }
}