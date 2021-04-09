package array.expression.structure.callchain.call

import array.expression.structure.callchain.Call
import array.expression.structure.expression.Expression
import java.math.BigInteger
import java.util.stream.Collectors

class Mapper(var expression: Expression<BigInteger>) : Call {
    override fun evaluate(arr: List<BigInteger>): List<BigInteger> {
        return arr.stream()
                .map { x -> expression.evaluate(x) }
                .collect(Collectors.toList())
    }

    override fun toString(): String {
        return "map{$expression}"
    }
}