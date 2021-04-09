package array.expression.structure.callchain.call

import array.expression.structure.callchain.Call
import array.expression.structure.expression.Expression
import java.math.BigInteger
import java.util.stream.Collectors

class Filter(val expression: Expression<Boolean>) : Call {
    override fun evaluate(arr: List<BigInteger>): List<BigInteger> {
        return arr.stream()
                .filter { x -> expression.evaluate(x)}
                .collect(Collectors.toList())
    }

    override fun toString(): String {
        return "filter{${expression}}"
    }
}