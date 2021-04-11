package array.expression.structure

import array.expression.simplify.Simplifier
import array.expression.structure.callchain.call.Filter
import array.expression.structure.callchain.call.Mapper
import java.math.BigInteger

class FilterMapper(val filter: Filter, val mapper: Mapper) : CallChain {
    override fun evaluate(arr: List<BigInteger>): List<BigInteger> {
        return mapper.evaluate(filter.evaluate(arr))
    }

    override fun toString(): String {
        return "$filter%>%$mapper"
    }

    fun simplify(): FilterMapper {
        Simplifier().simplifyFilter(filter)
        Simplifier().simplifyMapper(mapper)
        return this
    }
}