package parser.structure.callchain.call

import parser.structure.callchain.Call
import parser.structure.expression.Expression
import java.util.stream.Collectors

class Mapper(private val expression: Expression) : Call {
    override fun evaluate(arr: List<Int>): List<Int> {
        return arr.stream()
                .map { x -> expression.evaluateInt(x) }
                .collect(Collectors.toList())
    }

    override fun toString(): String {
        return "map{$expression}"
    }
}